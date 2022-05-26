package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.HabitsDao;
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
}
