package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.HabitTracker;

public interface HabitTrackerService {
    HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams);

    void patchHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams, PatchRequest patchRequest);
}
