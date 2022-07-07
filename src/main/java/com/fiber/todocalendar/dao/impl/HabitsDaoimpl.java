package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitsDao;
import com.fiber.todocalendar.dto.HabitsPatchRequest;
import com.fiber.todocalendar.dto.HabitsRequest;
import com.fiber.todocalendar.model.Habit;
import com.fiber.todocalendar.model.Habits;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HabitsDaoimpl implements HabitsDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Habits getHabitsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, Habits.class);
    }

    private boolean isHabitsExists(String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.exists(query, "habits");
    }

    @Override
    public void addHabit(String userId, HabitsPatchRequest habitsPatchRequest) {
        String name = habitsPatchRequest.getValue().get("name").toString();
        String checkColor = habitsPatchRequest.getValue().get("checkColor").toString();
        Habit habit = new Habit(name, checkColor);
        Query query = new Query(Criteria.where("userId").is(userId));

        if (!isHabitsExists(userId)) {
            createHabits(userId);
        }

        Update update = new Update()
                .push("habitList", habit).set("lastModifiedTime", new Date());

        mongoTemplate.updateFirst(query, update, "habits");
    }

    @Override
    public void replaceHabit(String userId, HabitsPatchRequest habitsPatchRequest) {
        String habitId = habitsPatchRequest.getValue().get("habitId").toString();
        String name = habitsPatchRequest.getValue().get("name").toString();
        String checkColor = habitsPatchRequest.getValue().get("checkColor").toString();
        Date now = new Date();

        Query query = Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("habitList").elemMatch(Criteria.where("habitId").is(habitId))));

        Update update = new Update()
                .set("habitList.$.name", name)
                .set("habitList.$.checkColor", checkColor)
                .set("habitList.$.lastModifiedTime", now)
                .set("lastModifiedTime", now);

        mongoTemplate.updateFirst(query, update, "habits");
    }

    @Override
    public void removeHabit(String userId, HabitsPatchRequest habitsPatchRequest) {
        String habitId = habitsPatchRequest.getValue().get("habitId").toString();
        Query query = Query.query(Criteria.where("userId").is(userId));

        Update update = new Update()
                .pull("habitList", new BasicDBObject("habitId", habitId))
                .set("lastModifiedTime", new Date());

        mongoTemplate.updateFirst(query, update, "habits");

    }

    @Override
    public void replaceHabits(String userId, HabitsRequest habitRequest) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        Update update = new Update()
                .set("habitList", habitRequest.getHabitList())
                .set("lastModifiedTime", new Date());
        mongoTemplate.updateFirst(query, update, "habits");
    }

    private void createHabits(String userId) {
        Habits habits = new Habits(userId);
        mongoTemplate.insert(habits, "habits");
    }
}
