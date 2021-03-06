package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitTrackerDao;
import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.HabitTrackerPatchRequest;
import com.fiber.todocalendar.model.HabitTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class HabitTrackerDaoImpl implements HabitTrackerDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams) {
        Query query = new Query(Criteria.where("habitId").is(habitTrackerQueryParams.getHabitId())
                .and("year").is(habitTrackerQueryParams.getYear()));
        HabitTracker habitTracker = mongoTemplate.findOne(query, HabitTracker.class);
        return habitTracker;
    }

    @Override
    public void addPickedDay(HabitTrackerQueryParams habitTrackerQueryParams, HabitTrackerPatchRequest habitTrackerPatchRequest) {
        Query query = new Query(Criteria.where("habitId").is(habitTrackerQueryParams.getHabitId())
                .and("year").is(habitTrackerQueryParams.getYear()));
        boolean isHabitTrackerExists = mongoTemplate.exists(query, "habitTrackers");
        if (!isHabitTrackerExists) {
            createHabitTracker(habitTrackerQueryParams);
        }
        Update update = new Update().push("pickedDays", habitTrackerPatchRequest.getValue()).set("lastModifiedTime", new Date());
        mongoTemplate.updateFirst(query, update, "habitTrackers");
    }

    @Override
    public void removePickedDay(HabitTrackerQueryParams habitTrackerQueryParams, HabitTrackerPatchRequest habitTrackerPatchRequest) {
        Query query = new Query(Criteria.where("habitId").is(habitTrackerQueryParams.getHabitId())
                .and("year").is(habitTrackerQueryParams.getYear()));
        Update update = new Update().pull("pickedDays", habitTrackerPatchRequest.getValue()).set("lastModifiedTime", new Date());
        mongoTemplate.updateFirst(query, update, "habitTrackers");
    }

    private void createHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams) {
        HabitTracker habitTracker = new HabitTracker(habitTrackerQueryParams.getHabitId(), habitTrackerQueryParams.getYear());
        mongoTemplate.insert(habitTracker, "habitTrackers");
    }
}
