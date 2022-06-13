package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.TodoListQueryParams;
import com.fiber.todocalendar.dto.TodoListPatchRequest;
import com.fiber.todocalendar.dto.TodoListRequest;
import com.fiber.todocalendar.model.TodoList;
import com.fiber.todocalendar.service.TodoListService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TodoListController {
    @Autowired
    private TodoListService todoListService;

    @GetMapping("/users/{userId}/todoLists")
    public ResponseEntity<TodoList> getTodoList(@PathVariable String userId, @RequestParam String date) {

        TodoListQueryParams todoListQueryParams = new TodoListQueryParams();
        todoListQueryParams.setUserId(userId);
        todoListQueryParams.setDate(date);

        TodoList todoList = todoListService.getTodoList(todoListQueryParams);

        if (todoList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(todoList);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PatchMapping("/users/{userId}/todoLists")
    public ResponseEntity<TodoList> patchTodoList(@PathVariable String userId, @RequestParam String date,
                                                  @RequestBody @Valid TodoListPatchRequest todoListPatchRequest) {

        TodoListQueryParams todoListQueryParams = new TodoListQueryParams();
        todoListQueryParams.setUserId(userId);
        todoListQueryParams.setDate(date);

        TodoList todoList = todoListService.patchTodoList(todoListQueryParams, todoListPatchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @PutMapping("/users/{userId}/todoLists")
    public ResponseEntity<TodoList> putTodoListsOrder(@PathVariable String userId, @RequestParam String date,
                                                      @RequestBody @Valid TodoListRequest todoListRequest) {

        TodoListQueryParams todoListQueryParams = new TodoListQueryParams();
        todoListQueryParams.setUserId(userId);
        todoListQueryParams.setDate(date);

        TodoList todoList = todoListService.putHabitsOrder(todoListQueryParams, todoListRequest);

        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }
}
