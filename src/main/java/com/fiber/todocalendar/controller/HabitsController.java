package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.model.HabitTrackers;
import com.fiber.todocalendar.model.Habits;
import com.fiber.todocalendar.service.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitsController {
    @Autowired
    private HabitsService habitsService;

    @GetMapping("/users/{userId}/habits")
    public ResponseEntity<Habits> getHabitTrackers(@PathVariable String userId) {
        Habits habits = habitsService.getHabitsByUserId(userId);
        if (habits != null) {
            return ResponseEntity.status(HttpStatus.OK).body(habits);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
