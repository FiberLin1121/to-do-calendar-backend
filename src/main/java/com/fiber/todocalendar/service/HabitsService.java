package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.HabitsPatchRequest;
import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.Habits;

public interface HabitsService {
    Habits getHabitsByUserId(String userId);

    void patchHabit(String userId, HabitsPatchRequest habitsPatchRequest);

    void putHabitsOrder(String userId, HabitsRequest habitRequest);
}
