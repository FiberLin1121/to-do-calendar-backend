package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.NotesDao;
import com.fiber.todocalendar.dto.NotesPatchRequest;
import com.fiber.todocalendar.dto.NotesRequest;
import com.fiber.todocalendar.model.Notes;
import com.fiber.todocalendar.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesDao notesDao;

    @Override
    public Notes getNotesByUserId(String userId) {
        return notesDao.getNotesByUserId(userId);
    }

    @Override
    public Notes patchNote(String userId, NotesPatchRequest notesPatchRequest) {
        Notes notes = null;
        switch (notesPatchRequest.getOp()) {
            case "add":
                notes = notesDao.addNote(userId, notesPatchRequest);
                break;
            case "replace":
                notes = notesDao.replaceNote(userId, notesPatchRequest);
                break;
            case "remove":
                notes = notesDao.removeNote(userId, notesPatchRequest);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return notes;
    }

    @Override
    public Notes putNotesOrder(String userId, NotesRequest notesRequest) {
        return notesDao.replaceNotes(userId, notesRequest);
    }
}
