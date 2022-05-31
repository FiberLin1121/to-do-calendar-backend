package com.fiber.todocalendar.service;

import com.fiber.todocalendar.model.HabitTracker;

public interface HabitTrackerService {
    HabitTracker getHabitTracker(String habitId, String year);
}
