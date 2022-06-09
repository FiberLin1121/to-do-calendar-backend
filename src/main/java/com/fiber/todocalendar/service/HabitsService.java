package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.Habits;

public interface HabitsService {
    Habits getHabitsByUserId(String userId);

    void patchHabit(String userId, PatchRequest patchRequest);

    void putHabitsOrder(String userId, HabitsRequest habitRequest);
}
