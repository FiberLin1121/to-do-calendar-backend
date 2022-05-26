package com.fiber.todocalendar.service;

import com.fiber.todocalendar.model.Habits;

public interface HabitsService {
    Habits getHabitsByUserId(String userId);
}
