package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.TodoListPatchRequest;
import com.fiber.todocalendar.dto.TodoListQueryParams;
import com.fiber.todocalendar.dto.TodoListRequest;
import com.fiber.todocalendar.model.TodoList;

public interface TodoListDao {
    TodoList getTodoList(TodoListQueryParams todoListQueryParams);
    TodoList replaceTodoList(TodoListQueryParams todoListQueryParams, TodoListRequest todoListRequest);

    TodoList addTask(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest);
    TodoList replaceTask(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest);
    TodoList removeTask(TodoListQueryParams todoListQueryParams, TodoListPatchRequest todoListPatchRequest);

}
