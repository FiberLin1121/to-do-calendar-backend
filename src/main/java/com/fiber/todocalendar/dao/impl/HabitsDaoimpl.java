package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitsDao;
import com.fiber.todocalendar.model.Habits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class HabitsDaoimpl implements HabitsDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根據 userId 查询原子習慣列表
     *
     * @param userId
     * @return
     */
    @Override
    public Habits getHabitsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Habits habits = mongoTemplate.findOne(query, Habits.class);
        System.out.println("habits = " + habits);
        return habits;
    }
}
