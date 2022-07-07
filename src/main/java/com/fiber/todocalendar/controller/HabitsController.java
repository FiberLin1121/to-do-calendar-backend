package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.HabitsPatchRequest;
import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.model.Habits;
import com.fiber.todocalendar.service.HabitsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Habit 相關 API")
@RestController
public class HabitsController {

    @Autowired
    private HabitsService habitsService;

    @ApiOperation(value = "取得使用者的原子習慣列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "使用者ID", dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/users/{userId}/habits")
    public ResponseEntity<Habits> getHabits(@PathVariable String userId) {
        Habits habits = habitsService.getHabitsByUserId(userId);
        if (habits != null) {
            return ResponseEntity.status(HttpStatus.OK).body(habits);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @ApiOperation(value = "異動使用者的原子習慣",
            notes = "此 API 的 Request body 的 op 請填 add 或 remove 或 replace，" +
                    "分別代表新增、刪除、修改操作；path 請填 /habitList；value 要帶操作的原子習慣，包含 habitId、name、checkColor 屬性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "使用者ID", dataTypeClass = String.class, paramType = "path"),
    })
    @PatchMapping("/users/{userId}/habits")
    public ResponseEntity<Habits> patchHabit(@PathVariable String userId,
                                             @RequestBody @Valid HabitsPatchRequest habitsPatchRequest) {
        habitsService.patchHabit(userId, habitsPatchRequest);
        Habits habits = habitsService.getHabitsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(habits);
    }

    @ApiOperation(value = "修改使用者的原子習慣列表排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "使用者ID", dataTypeClass = String.class, paramType = "path"),
    })
    @PutMapping("/users/{userId}/habits")
    public ResponseEntity<Habits> putHabitsOrder(@PathVariable String userId,
                                                 @RequestBody @Valid HabitsRequest habitRequest) {
        habitsService.putHabitsOrder(userId, habitRequest);
        Habits habits = habitsService.getHabitsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(habits);
    }
}
