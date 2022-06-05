package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.HabitTracker;

public interface HabitTrackerDao {

    HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams);

    void addPickedDay(HabitTrackerQueryParams habitTrackerQueryParams, PatchRequest patchRequest);

    void removePickedDay(HabitTrackerQueryParams habitTrackerQueryParams, PatchRequest patchRequest);
}
