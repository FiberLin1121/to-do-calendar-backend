package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitTrackerDao;
import com.fiber.todocalendar.model.HabitTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class HabitTrackerDaoImpl implements HabitTrackerDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根據 userId, year 條件查询原子習慣追蹤
     *
     * @param habitId
     * @param year
     * @return
     */
    @Override
    public HabitTracker getHabitTracker(String habitId, String year) {
        Query query = new Query(Criteria.where("habitId").is(habitId).and("year").is(year));
        HabitTracker habitTracker = mongoTemplate.findOne(query, HabitTracker.class);
        System.out.println("habitTracker = " + habitTracker);
        return habitTracker;
    }
}
