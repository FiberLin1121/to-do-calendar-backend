package com.fiber.todocalendar;

import com.fiber.todocalendar.model.HabitTrackers;
import com.fiber.todocalendar.model.Habits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
class TodoCalendarApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void queryTest() {
        Query query=new Query(Criteria.where("userId").is("user01"));
        Habits habits =  mongoTemplate.findOne(query , Habits.class);
        System.out.println("habits = " + habits);
//        HabitTrackers habitTrackers = new  HabitTrackers();
//        habitTrackers.setUserId("123");
//        HabitTrackers habitTrackers1 =mongoTemplate.insert(habitTrackers);
//        System.out.println("habitTrackers1 = " + habitTrackers1);
    }

}
