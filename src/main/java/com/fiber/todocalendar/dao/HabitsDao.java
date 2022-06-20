package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.HabitsPatchRequest;
import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.Habits;

public interface HabitsDao {
    Habits getHabitsByUserId(String userId);

    void replaceHabits(String userId, HabitsRequest habitRequest);

    void addHabit(String userId, HabitsPatchRequest habitsPatchRequest);

    void replaceHabit(String userId, HabitsPatchRequest habitsPatchRequest);

    void removeHabit(String userId, HabitsPatchRequest habitsPatchRequest);

}
