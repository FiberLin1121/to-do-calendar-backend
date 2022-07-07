package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.NotesDao;
import com.fiber.todocalendar.dto.NotesPatchRequest;
import com.fiber.todocalendar.dto.NotesRequest;
import com.fiber.todocalendar.model.Note;
import com.fiber.todocalendar.model.Notes;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;


@Component
public class NotesDaoImpl implements NotesDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Notes getNotesByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, Notes.class);
    }

    @Override
    public Notes addNote(String userId, NotesPatchRequest notesPatchRequest) {
        Query query = new Query(Criteria.where("userId").is(userId));
        String name = notesPatchRequest.getValue().get("name").toString();
        String content = notesPatchRequest.getValue().get("content").toString();
        String bgColor = notesPatchRequest.getValue().get("bgColor").toString();
        String tapeStyle = notesPatchRequest.getValue().get("tapeStyle").toString();
        Note note = new Note(name, content, bgColor, tapeStyle);

        boolean isNotesExists = mongoTemplate.exists(query, Notes.class);
        if (!isNotesExists) {
            createNotes(userId);
        }

        Update update = new Update().push("list1", note).set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, Notes.class);
    }

    @Override
    public Notes replaceNote(String userId, NotesPatchRequest notesPatchRequest) {
        String noteId = notesPatchRequest.getValue().get("noteId").toString();
        String name = notesPatchRequest.getValue().get("name").toString();
        String content = notesPatchRequest.getValue().get("content").toString();
        String bgColor = notesPatchRequest.getValue().get("bgColor").toString();
        String tapeStyle = notesPatchRequest.getValue().get("tapeStyle").toString();
        Date now = new Date();

        String targetColumn = identifyPath(notesPatchRequest.getPath());

        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where(targetColumn).elemMatch(Criteria.where("noteId").is(noteId))));

        Update update = new Update()
                .set(targetColumn + ".$.name", name)
                .set(targetColumn + ".$.content", content)
                .set(targetColumn + ".$.bgColor", bgColor)
                .set(targetColumn + ".$.tapeStyle", tapeStyle)
                .set(targetColumn + ".$.lastModifiedTime", now)
                .set("lastModifiedTime", now);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, Notes.class);
    }

    @Override
    public Notes removeNote(String userId, NotesPatchRequest notesPatchRequest) {
        String noteId = notesPatchRequest.getValue().get("noteId").toString();
        Query query = new Query(Criteria.where("userId").is(userId));

        String targetColumn = identifyPath(notesPatchRequest.getPath());

        Update update = new Update()
                .pull(targetColumn, new BasicDBObject("noteId", noteId))
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, Notes.class);
    }

    @Override
    public Notes replaceNotes(String userId, NotesRequest notesRequest) {
        Query query = Query.query(Criteria.where("userId").is(userId));

        Update update = new Update()
                .set("list1", notesRequest.getList1())
                .set("list2", notesRequest.getList2())
                .set("list3", notesRequest.getList3())
                .set("list4", notesRequest.getList4())
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, Notes.class);
    }

    private void createNotes(String userId) {
        Notes notes = new Notes(userId);
        mongoTemplate.insert(notes, "notes");
    }

    private String identifyPath(String path) {
        String targetColumn = null;
        switch (path) {
            case "/list1":
                targetColumn = "list1";
                break;
            case "/list2":
                targetColumn = "list2";
                break;
            case "/list3":
                targetColumn = "list3";
                break;
            case "/list4":
                targetColumn = "list4";
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return targetColumn;
    }
}
