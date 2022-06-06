package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitsDao;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.Habit;
import com.fiber.todocalendar.model.Habits;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        return habits;
    }

    /**
     * 查詢原子習慣列表是否存在
     *
     * @param userId
     */
    private boolean isHabitsExists(String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.exists(query, "habits");
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

        if (!isHabitsExists(userId)) {
            createHabits(userId);
        }

        Update update = new Update()
                .push("habitList", habit).set("lastModifiedTime", System.currentTimeMillis());

        mongoTemplate.updateFirst(query, update, "habits");
    }

    /**
     * 修改原子習慣
     *
     * @param userId
     * @param patchRequest
     */
    @Override
    public void replaceHabit(String userId, PatchRequest patchRequest) {
        String habitId = patchRequest.getValue().get("habitId").toString();
        String name = patchRequest.getValue().get("name").toString();
        String checkColor = patchRequest.getValue().get("checkColor").toString();
        long now = System.currentTimeMillis();

        Update update = new Update()
                .set("habitList.$.name", name)
                .set("habitList.$.checkColor", checkColor)
                .set("habitList.$.lastModifiedTime", now)
                .set("lastModifiedTime", now);

        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("habitList").elemMatch(Criteria.where("habitId").is(habitId))));

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
        String habitId = patchRequest.getValue().get("habitId").toString();
        Query query = Query.query(Criteria.where("userId").is(userId));

        Update update = new Update()
                .pull("habitList", new BasicDBObject("habitId", habitId))
                .set("lastModifiedTime", System.currentTimeMillis());

        mongoTemplate.updateFirst(query, update, "habits");

    }

    /**
     * 建立原子習慣列表
     *
     * @param userId
     */
    private void createHabits(String userId) {
        Habits habits = new Habits(userId);
        mongoTemplate.insert(habits, "habits");
    }
}
