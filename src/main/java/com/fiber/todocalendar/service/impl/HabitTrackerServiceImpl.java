package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.HabitTrackerDao;
import com.fiber.todocalendar.dto.HabitTrackerCreateParams;
import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.HabitTracker;
import com.fiber.todocalendar.service.HabitTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HabitTrackerServiceImpl implements HabitTrackerService {
    @Autowired
    private HabitTrackerDao habitTrackerDao;

    @Override
    public HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams) {
        return habitTrackerDao.getHabitTracker(habitTrackerQueryParams);
    }

    @Override
    public void patchHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams, PatchRequest patchRequest) {
        switch (patchRequest.getOp()) {
            case "add":
                habitTrackerDao.addPickedDay(habitTrackerQueryParams, patchRequest);
                break;
            case "remove":
                habitTrackerDao.removePickedDay(habitTrackerQueryParams, patchRequest);
                break;
        }
    }
}
