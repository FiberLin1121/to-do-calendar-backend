package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.config.DefaultValueProperties;
import com.fiber.todocalendar.dto.TodoListQueryParams;
import com.fiber.todocalendar.dto.TodoListPatchRequest;
import com.fiber.todocalendar.dto.TodoListRequest;
import com.fiber.todocalendar.model.Label;
import com.fiber.todocalendar.model.TodoList;
import com.fiber.todocalendar.service.TodoListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "TodoList 相關 API")
@RestController
public class TodoListController {

    private final static Logger log = LoggerFactory.getLogger(TodoListController.class);

    @Autowired
    private TodoListService todoListService;

    @Autowired
    DefaultValueProperties defaultValueProperties;

    @GetMapping("/labels")
    public ResponseEntity<Label> getLabelDefaultValue() {
        Label label = new Label();
        label.setFirstColor(defaultValueProperties.getFirstColor());
        label.setSecondColor(defaultValueProperties.getSecondColor());
        label.setThirdColor(defaultValueProperties.getThirdColor());
        label.setFourthColor(defaultValueProperties.getFourthColor());
        return ResponseEntity.status(HttpStatus.OK).body(label);
    }

    @ApiOperation(value = "取得使用者的指定日期的代辦事項")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "使用者ID", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "date", value = "日期", dataTypeClass = String.class, paramType = "query")
    })
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

    @ApiOperation(value = "異動使用者的指定日期的代辦事項",
            notes = "此 API 的 Request body 的 op 請填 add 或 remove 或 replace，" +
                    "分別代表新增、刪除、修改操作；path 請填 /todoList 或 /doneList；value 要帶操作的代辦事項，包含 taskId、name、labelType 屬性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "使用者ID", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "date", value = "日期", dataTypeClass = String.class, paramType = "query")
    })
    @PatchMapping("/users/{userId}/todoLists")
    public ResponseEntity<TodoList> patchTodoList(@PathVariable String userId, @RequestParam String date,
                                                  @RequestBody @Valid TodoListPatchRequest todoListPatchRequest) {

        TodoListQueryParams todoListQueryParams = new TodoListQueryParams();
        todoListQueryParams.setUserId(userId);
        todoListQueryParams.setDate(date);

        TodoList todoList = todoListService.patchTodoList(todoListQueryParams, todoListPatchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @ApiOperation(value = "修改使用者的指定日期的代辦事項排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "使用者ID", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "date", value = "日期", dataTypeClass = String.class, paramType = "query")
    })
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
