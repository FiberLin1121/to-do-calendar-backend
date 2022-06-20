package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.UserPatchRequest;
import com.fiber.todocalendar.dto.UserRegisterRequest;
import com.fiber.todocalendar.model.User;

public interface UserDao {
    User getUserById(String user);

    User getUserByEmail(String email);

    User createUser(UserRegisterRequest userRegisterRequest);

    User replaceName(String userId, UserPatchRequest userPatchRequest);

    User replacePassword(String userId, UserPatchRequest userPatchRequest);

    User replaceLabelSetting(String userId, UserPatchRequest userPatchRequest);
}
