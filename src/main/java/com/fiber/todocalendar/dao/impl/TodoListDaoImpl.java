package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.TodoListDao;
import com.fiber.todocalendar.dto.TodoListPatchRequest;
import com.fiber.todocalendar.dto.TodoListQueryParams;
import com.fiber.todocalendar.dto.TodoListRequest;
import com.fiber.todocalendar.model.Task;
import com.fiber.todocalendar.model.TodoList;
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
public class TodoListDaoImpl implements TodoListDao {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 查詢代辦事項
     *
     * @param todoListQueryParams
     */
    @Override
    public TodoList getTodoList(TodoListQueryParams todoListQueryParams) {
        Query query = new Query(Criteria.where("userId").is(todoListQueryParams.getUserId()).and("date").is(todoListQueryParams.getDate()));
        return mongoTemplate.findOne(query, TodoList.class);
    }

    /**
     * 增加代辦事項
     *
     * @param todoListQueryParams
     * @param todoListPatchRequest
     */
    @Override
    public TodoList addTask(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest) {
        String name = todoListPatchRequest.getValue().get("name").toString();
        String labelType = todoListPatchRequest.getValue().get("labelType").toString();
        Task task = new Task(name, labelType);
        Query query = new Query(Criteria.where("userId").is(todoListQueryParams.getUserId()).and("date").is(todoListQueryParams.getDate()));

        boolean isTodoListExists = mongoTemplate.exists(query, "todoLists");
        if (!isTodoListExists) {
            createTodoList(todoListQueryParams);
        }

        Update update = new Update().push("todoList", task).set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, TodoList.class);
    }

    /**
     * 修改代辦事項
     *
     * @param todoListQueryParams
     * @param todoListPatchRequest
     */
    @Override
    public TodoList replaceTask(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest) {
        String taskId = todoListPatchRequest.getValue().get("taskId").toString();
        String name = todoListPatchRequest.getValue().get("name").toString();
        String labelType = todoListPatchRequest.getValue().get("labelType").toString();
        Date now = new Date();

        String targetColumn = identfyPath(todoListPatchRequest.getPath());

        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(todoListQueryParams.getUserId()),
                Criteria.where("date").is(todoListQueryParams.getDate()),
                Criteria.where(targetColumn).elemMatch(Criteria.where("taskId").is(taskId))));

        Update update = new Update()
                .set(targetColumn + ".$.name", name)
                .set(targetColumn + ".$.labelType", labelType)
                .set(targetColumn + ".$.lastModifiedTime", now)
                .set("lastModifiedTime", now);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, TodoList.class);
    }

    /**
     * 移除代辦事項
     *
     * @param todoListQueryParams
     * @param todoListPatchRequest
     */
    @Override
    public TodoList removeTask(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest) {
        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(todoListQueryParams.getUserId()),
                Criteria.where("date").is(todoListQueryParams.getDate())));

        String targetColumn = identfyPath(todoListPatchRequest.getPath());

        Update update = new Update()
                .pull(targetColumn, new BasicDBObject("taskId", todoListPatchRequest.getValue().get("taskId").toString()))
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, TodoList.class);
    }

    /**
     * 修改代辦事項排序
     *
     * @param todoListQueryParams
     * @param todoListRequest
     */
    @Override
    public TodoList replaceTodoList(TodoListQueryParams todoListQueryParams, TodoListRequest todoListRequest) {
        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(todoListQueryParams.getUserId()),
                Criteria.where("date").is(todoListQueryParams.getDate())));

        Update update = new Update()
                .set("todoList", todoListRequest.getTodoList())
                .set("doneList", todoListRequest.getDoneList())
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, TodoList.class);
    }

    /**
     * 建立代辦事項清單
     *
     * @param todoListQueryParams
     * @return
     */
    private void createTodoList(TodoListQueryParams todoListQueryParams) {
        TodoList task = new TodoList(todoListQueryParams.getUserId(), todoListQueryParams.getDate());
        mongoTemplate.insert(task, "todoLists");
    }

    private String identfyPath(String path) {
        String targetColumn = null;
        switch (path) {
            case "/todoList":
                targetColumn = "todoList";
                break;
            case "/doneList":
                targetColumn = "doneList";
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return targetColumn;
    }
}
