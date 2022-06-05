package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitsDao;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.Habit;
import com.fiber.todocalendar.model.Habits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
        return habits;
    }

    /**
     * 增加原子習慣
     *
     * @param userId
     * @param patchRequest
     */
    @Override
    public void addHabit(String userId, PatchRequest patchRequest) {
        String name = patchRequest.getValue().get("name").toString();
        String checkColor = patchRequest.getValue().get("checkColor").toString();
        Habit habit = new Habit(name, checkColor);
        Query query = new Query(Criteria.where("userId").is(userId));
        boolean isHabitsExists = mongoTemplate.exists(query, "habitTrackers");
        if (!isHabitsExists) {
            createHabits(userId);
        }
        Update update = new Update().push( "habitList" , habit).set("lastModifiedTime", System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, "habits");
    }

    /**
     * 移除原子習慣
     *
     * @param userId
     * @param patchRequest
     */
    @Override
    public void removeHabit(String userId, PatchRequest patchRequest) {
        Query query = new Query(Criteria.where("userId").is(userId));
        boolean isHabitsExists = mongoTemplate.exists(query, "habitTrackers");
        if (isHabitsExists) {
            Update update = new Update().push( "habitList" , patchRequest.getValue()).set("lastModifiedTime", System.currentTimeMillis());
            mongoTemplate.updateFirst(query, update, "habits");
        }
    }

    /**
     * 建立原子習慣列表
     *
     * @param userId
     */
    private void createHabits(String userId){
        Habits habits = new Habits(userId);
        mongoTemplate.insert(habits,"habits");
    }
}
