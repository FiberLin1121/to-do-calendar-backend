package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.HabitTracker;
import com.fiber.todocalendar.service.HabitTrackerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "HabitTracker 相關 API")
@RestController
public class HabitTrackerController {
    @Autowired
    private HabitTrackerService habitTrackerService;

    @ApiOperation(value = "取得使用者的原子習慣紀錄")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "habitId", value = "原子習慣ID", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataTypeClass = String.class, paramType = "query")
    })
    @GetMapping("/users/{userId}/habitTrackers")
    public ResponseEntity<HabitTracker> getHabitTracker(@RequestParam String habitId, @RequestParam String year) {
        HabitTrackerQueryParams habitTrackerQueryParams = new HabitTrackerQueryParams();
        habitTrackerQueryParams.setHabitId(habitId);
        habitTrackerQueryParams.setYear(year);

        HabitTracker habitTracker = habitTrackerService.getHabitTracker(habitTrackerQueryParams);

        if (habitTracker != null) {
            return ResponseEntity.status(HttpStatus.OK).body(habitTracker);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @ApiOperation(value = "異動使用者的原子習慣紀錄", notes="此 API 的 Request body 的 op 請填 add 或 remove，分別代表打圈操作與取消操作；path 請填：/pickedDays；value 要帶操作的日期物件，包含 id、date 屬性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "habitId", value = "原子習慣ID", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "year", value = "指定年份", dataTypeClass = String.class, paramType = "query")
    })
    @PatchMapping("/users/{userId}/habitTrackers")
    public ResponseEntity<HabitTracker> patchHabitTracker(@RequestParam String habitId, @RequestParam String year,
                                                          @RequestBody @Valid HabitTrackerPatchRequest habitTrackerPatchRequest) {

        HabitTrackerQueryParams habitTrackerQueryParams = new HabitTrackerQueryParams();
        habitTrackerQueryParams.setHabitId(habitId);
        habitTrackerQueryParams.setYear(year);

        habitTrackerService.patchHabitTracker(habitTrackerQueryParams, habitTrackerPatchRequest);

        HabitTracker habitTracker = habitTrackerService.getHabitTracker(habitTrackerQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(habitTracker);
    }
}
