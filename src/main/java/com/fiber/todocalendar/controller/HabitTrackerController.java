package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.model.HabitTracker;
import com.fiber.todocalendar.model.Habits;
import com.fiber.todocalendar.service.HabitTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitTrackerController {
    @Autowired
    private HabitTrackerService habitTrackerService;

    @GetMapping("/users/{userId}/habitTracker")
    public ResponseEntity<HabitTracker> getHabitTracker(@RequestParam String habitId, @RequestParam String year) {
        HabitTracker defaultResult = new HabitTracker();
        HabitTracker habitTracker = habitTrackerService.getHabitTracker(habitId, year);
        if (habitTracker != null) {
            return ResponseEntity.status(HttpStatus.OK).body(habitTracker);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(defaultResult);
        }
    }
}
