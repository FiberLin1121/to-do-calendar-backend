package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.HabitTracker;
import com.fiber.todocalendar.service.HabitTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class HabitTrackerController {
    @Autowired
    private HabitTrackerService habitTrackerService;

    @GetMapping("/users/{userId}/habitTrackers")
    public ResponseEntity<HabitTracker> getHabitTracker(@RequestParam String habitId, @RequestParam String year) {
        HabitTrackerQueryParams habitTrackerQueryParams = new HabitTrackerQueryParams();
        habitTrackerQueryParams.setHabitId(habitId);
        habitTrackerQueryParams.setYear(year);

        HabitTracker habitTracker = habitTrackerService.getHabitTracker(habitTrackerQueryParams);

        if (habitTracker != null) {
            return ResponseEntity.status(HttpStatus.OK).body(habitTracker);
        } else {
            HabitTracker defaultResult = new HabitTracker(habitTrackerQueryParams.getHabitId(), habitTrackerQueryParams.getYear());
            return ResponseEntity.status(HttpStatus.OK).body(defaultResult);
        }
    }

    @PatchMapping("/users/{userId}/habitTrackers")
    public ResponseEntity<HabitTracker> patchHabitTracker(@RequestParam String habitId, @RequestParam String year,
                                                          @RequestBody @Valid PatchRequest patchRequest) {

        HabitTrackerQueryParams habitTrackerQueryParams = new HabitTrackerQueryParams();
        habitTrackerQueryParams.setHabitId(habitId);
        habitTrackerQueryParams.setYear(year);

        habitTrackerService.patchHabitTracker(habitTrackerQueryParams, patchRequest);

        HabitTracker habitTracker = habitTrackerService.getHabitTracker(habitTrackerQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(habitTracker);
    }
}
