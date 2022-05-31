package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.HabitTrackerDao;
import com.fiber.todocalendar.model.HabitTracker;
import com.fiber.todocalendar.service.HabitTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HabitTrackerServiceImpl implements HabitTrackerService {
    @Autowired
    private HabitTrackerDao habitTrackerDao;

    @Override
    public HabitTracker getHabitTracker(String habitId, String year) {
        return habitTrackerDao.getHabitTracker(habitId, year);
    }
}
