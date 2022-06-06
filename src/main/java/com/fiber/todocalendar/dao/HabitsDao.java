package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.Habits;

public interface HabitsDao {
    Habits getHabitsByUserId(String userId);

    void addHabit(String userId, PatchRequest patchRequest);

    void replaceHabit(String userId, PatchRequest patchRequest);

    void removeHabit(String userId, PatchRequest patchRequest);
}
