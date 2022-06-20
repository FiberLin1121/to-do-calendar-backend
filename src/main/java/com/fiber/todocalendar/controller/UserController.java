package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.UserLoginRequest;
import com.fiber.todocalendar.dto.UserPatchRequest;
import com.fiber.todocalendar.dto.UserRegisterRequest;
import com.fiber.todocalendar.model.User;
import com.fiber.todocalendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequest userRegisterRequest){
        User user = userService.register(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable String userId,
                                          @RequestBody @Valid UserPatchRequest userPatchRequest) {
        User user = userService.patchUser(userId, userPatchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
