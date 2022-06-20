package com.fiber.todocalendar;

import com.fiber.todocalendar.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootTest
class TodoCalendarApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    private UserDao userDao;

    @Test
    void queryTest() {
//        User user = new User("name", "123", "sss");
//        Object result = mongoTemplate.insert(user, "users");
//        System.out.println("user = " + user);
//        User user = userDao.getUserByEmail("test01@gmail.com");
//        System.out.println("user = " + user);
    }
}
