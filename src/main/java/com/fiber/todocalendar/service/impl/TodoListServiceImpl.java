package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.TodoListDao;
import com.fiber.todocalendar.dto.TodoListPatchRequest;
import com.fiber.todocalendar.dto.TodoListQueryParams;
import com.fiber.todocalendar.dto.TodoListRequest;
import com.fiber.todocalendar.model.TodoList;
import com.fiber.todocalendar.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TodoListServiceImpl implements TodoListService {
    @Autowired
    TodoListDao todoListDao;

    @Override
    public TodoList getTodoList(TodoListQueryParams todoListQueryParams) {
        return todoListDao.getTodoList(todoListQueryParams);
    }

    @Override
    public TodoList patchTodoList(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest) {
        TodoList todoList = null;

        switch (todoListPatchRequest.getOp()) {
            case "add":
                todoList = todoListDao.addTask(todoListQueryParams, todoListPatchRequest);
                break;
            case "replace":
                 todoList = getTodoList(todoListQueryParams);
                if (todoList == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                todoList = todoListDao.replaceTask(todoListQueryParams, todoListPatchRequest);
                break;
            case "remove":
                todoList = todoListDao.removeTask(todoListQueryParams, todoListPatchRequest);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return todoList;
    }

    @Override
    public TodoList putHabitsOrder(TodoListQueryParams todoListQueryParams, TodoListRequest todoListRequest) {
        return todoListDao.replaceTodoList(todoListQueryParams, todoListRequest);
    }
}
