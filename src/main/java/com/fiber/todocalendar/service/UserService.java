package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.*;
import com.fiber.todocalendar.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    User getUserById(String userId);

    User getUserByEmail(String email);

    User register(UserRegisterRequest userRegisterRequest);

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    User patchUser(String userId, UserPatchRequest userPatchRequest);

    void logout();
}
