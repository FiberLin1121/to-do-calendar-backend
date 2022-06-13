package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.TodoListPatchRequest;
import com.fiber.todocalendar.dto.TodoListQueryParams;
import com.fiber.todocalendar.dto.TodoListRequest;
import com.fiber.todocalendar.model.TodoList;

public interface TodoListService {
    TodoList getTodoList(TodoListQueryParams todoListQueryParams);
    TodoList patchTodoList(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest);
    TodoList putHabitsOrder(TodoListQueryParams todoListQueryParams, TodoListRequest todoListRequest);
}
