package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.UserDao;
import com.fiber.todocalendar.dto.UserLoginRequest;
import com.fiber.todocalendar.dto.UserPatchRequest;
import com.fiber.todocalendar.dto.UserRegisterRequest;
import com.fiber.todocalendar.exception.*;
import com.fiber.todocalendar.model.User;
import com.fiber.todocalendar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User register(UserRegisterRequest userRegisterRequest) {
        // 檢查帳號是否重複註冊
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user != null) {
            log.warn("該 email:{} 已被註冊", userRegisterRequest.getEmail());
            throw new AccountDuplicateException("該 email 已被註冊");
        }
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        // 檢查帳號是否存在
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if (user == null) {
            log.warn("該 email:{} 尚未註冊", userLoginRequest.getEmail());
            throw new AccountNotFoundException("帳號或密碼錯誤");
        }
        if(!user.getPassword().equals(userLoginRequest.getPassword())){
            log.warn("該 email:{} 密碼輸入錯誤", userLoginRequest.getEmail());
            throw new PasswordNotMatchException("帳號或密碼錯誤");
        }
        return user;
    }

    @Override
    public User patchUser(String userId, UserPatchRequest userPatchRequest) {
        User user = null;
        switch (userPatchRequest.getPath()) {
            case "/name":
                user = userDao.replaceName(userId, userPatchRequest);
                break;
            case "/password":
                user = userDao.getUserById(userId);
                if (user.getPassword().equals(userPatchRequest.getValue().get("oldPassword").toString())) {
                    user = userDao.replacePassword(userId, userPatchRequest);
                } else {
                    throw new OldPasswordNotMatchException("舊密碼不符");
                }
                break;
            case "/labelSetting":
                user = userDao.replaceLabelSetting(userId, userPatchRequest);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return user;
    }
}
