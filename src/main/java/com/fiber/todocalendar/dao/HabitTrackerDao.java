package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.HabitTracker;

public interface HabitTrackerDao {

    HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams);

    void addPickedDay(HabitTrackerQueryParams habitTrackerQueryParams, HabitTrackerPatchRequest habitTrackerPatchRequest);

    void removePickedDay(HabitTrackerQueryParams habitTrackerQueryParams, HabitTrackerPatchRequest habitTrackerPatchRequest);
}
