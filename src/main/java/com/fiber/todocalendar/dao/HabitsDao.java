package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.model.Habits;

public interface HabitsDao {
    Habits getHabitsByUserId(String userId);
}
