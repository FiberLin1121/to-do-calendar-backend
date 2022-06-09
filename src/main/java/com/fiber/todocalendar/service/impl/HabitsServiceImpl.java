package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.HabitsDao;
import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.Habits;
import com.fiber.todocalendar.service.HabitsService;
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
    public void patchHabit(String userId, PatchRequest patchRequest) {
        switch (patchRequest.getOp()) {
            case "add":
                habitsDao.addHabit(userId, patchRequest);
                break;
            case "replace":
                habitsDao.replaceHabit(userId, patchRequest);
                break;
            case "remove":
                habitsDao.removeHabit(userId, patchRequest);
                break;
        }
    }

    @Override
    public void putHabitsOrder(String userId, HabitsRequest habitRequest) {
        habitsDao.replaceHabits(userId, habitRequest);
    }
}
