package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.UserLoginRequest;
import com.fiber.todocalendar.dto.UserPatchRequest;
import com.fiber.todocalendar.dto.UserRegisterRequest;
import com.fiber.todocalendar.model.User;

public interface UserService {
    User getUserById(String userId);

    User register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

    User patchUser(String userId, UserPatchRequest userPatchRequest);
}
