package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.HabitTracker;

public interface HabitTrackerService {
    HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams);

    void patchHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams, HabitTrackerPatchRequest habitTrackerPatchRequest);
}
