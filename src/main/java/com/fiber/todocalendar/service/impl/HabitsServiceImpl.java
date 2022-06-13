package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.HabitsDao;
import com.fiber.todocalendar.service.HabitsService;
import com.fiber.todocalendar.dto.HabitsPatchRequest;
import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.model.Habits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HabitsServiceImpl implements HabitsService {
    @Autowired
    private HabitsDao habitsDao;

    @Override
    public Habits getHabitsByUserId(String userId) {
        return habitsDao.getHabitsByUserId(userId);
    }

    @Override
    public void patchHabit(String userId, HabitsPatchRequest habitsPatchRequest) {
        switch (habitsPatchRequest.getOp()) {
            case "add":
                habitsDao.addHabit(userId, habitsPatchRequest);
                break;
            case "replace":
                habitsDao.replaceHabit(userId, habitsPatchRequest);
                break;
            case "remove":
                habitsDao.removeHabit(userId, habitsPatchRequest);
                break;
        }
    }

    @Override
    public void putHabitsOrder(String userId, HabitsRequest habitRequest) {
        habitsDao.replaceHabits(userId, habitRequest);
    }
}
