package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.UserDao;
import com.fiber.todocalendar.dto.UserPatchRequest;
import com.fiber.todocalendar.dto.UserRegisterRequest;
import com.fiber.todocalendar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User getUserById(String userId) {
        Query query = new Query(Criteria.where("id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User getUserByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User createUser(UserRegisterRequest userRegisterRequest) {
        User user = new User(userRegisterRequest.getName(), userRegisterRequest.getEmail(), userRegisterRequest.getPassword());
        return mongoTemplate.insert(user, "users");
    }

    @Override
    public User replaceName(String userId, UserPatchRequest userPatchRequest) {
        Query query = new Query(Criteria.where("id").is(userId));

        Update update = new Update()
                .set("name", userPatchRequest.getValue().get("name").toString())
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, User.class);
    }

    @Override
    public User replacePassword(String userId, UserPatchRequest userPatchRequest) {
        Query query = new Query(Criteria.where("id").is(userId));

        Update update = new Update()
                .set("password", userPatchRequest.getValue().get("password").toString())
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, User.class);
    }

    @Override
    public User replaceLabelSetting(String userId, UserPatchRequest userPatchRequest) {
        Query query = new Query(Criteria.where("id").is(userId));

        System.out.println("firstColor = " + userPatchRequest.getValue().get("firstColor").toString());

        Update update = new Update()
                .set("labelSetting.firstColor", userPatchRequest.getValue().get("firstColor").toString())
                .set("labelSetting.secondColor", userPatchRequest.getValue().get("secondColor").toString())
                .set("labelSetting.thirdColor", userPatchRequest.getValue().get("thirdColor").toString())
                .set("labelSetting.fourthColor", userPatchRequest.getValue().get("fourthColor").toString())
                .set("lastModifiedTime", new Date());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        User user = mongoTemplate.findAndModify(query, update, options, User.class);
        return user;
//        return mongoTemplate.findAndModify(query, update, options, User.class);
    }
}
