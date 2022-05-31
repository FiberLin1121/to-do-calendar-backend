package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.model.HabitTracker;

public interface HabitTrackerDao {

    HabitTracker getHabitTracker(String habitId, String year);
}
