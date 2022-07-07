package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.HabitTrackerDao;
import com.fiber.todocalendar.service.HabitTrackerService;
import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.HabitTracker;
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
    public void patchHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams, HabitTrackerPatchRequest habitTrackerPatchRequest) {
        switch (habitTrackerPatchRequest.getOp()) {
            case "add":
                habitTrackerDao.addPickedDay(habitTrackerQueryParams, habitTrackerPatchRequest);
                break;
            case "remove":
                habitTrackerDao.removePickedDay(habitTrackerQueryParams, habitTrackerPatchRequest);
                break;
        }
    }
}
