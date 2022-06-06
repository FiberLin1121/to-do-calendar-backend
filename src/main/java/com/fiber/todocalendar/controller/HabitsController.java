package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.Habits;
import com.fiber.todocalendar.service.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HabitsController {
    @Autowired
    private HabitsService habitsService;

    @GetMapping("/users/{userId}/habits")
    public ResponseEntity<Habits> getHabits(@PathVariable String userId) {
        Habits habits = habitsService.getHabitsByUserId(userId);
        if (habits != null) {
            return ResponseEntity.status(HttpStatus.OK).body(habits);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/users/{userId}/habits")
    public ResponseEntity<Habits> patchHabit(@PathVariable String userId,
                                                                    @RequestBody @Valid PatchRequest patchRequest) {
        habitsService.patchHabit(userId, patchRequest);
        Habits habits = habitsService.getHabitsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(habits);
    }
}
