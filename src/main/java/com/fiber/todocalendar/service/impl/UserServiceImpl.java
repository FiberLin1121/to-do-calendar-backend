package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.UserDao;
import com.fiber.todocalendar.dto.UserLoginRequest;
import com.fiber.todocalendar.dto.UserLoginResponse;
import com.fiber.todocalendar.dto.UserPatchRequest;
import com.fiber.todocalendar.dto.UserRegisterRequest;
import com.fiber.todocalendar.exception.*;
import com.fiber.todocalendar.model.LoginUser;
import com.fiber.todocalendar.model.User;
import com.fiber.todocalendar.service.UserService;
import com.fiber.todocalendar.utils.JwtUtil;
import com.fiber.todocalendar.utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Objects;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
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
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        // authenticationManager authenticate 進行使用者認證
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 認證通過 -> 使用 userId 生成一個 JWT 並返回
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId());

        // 把完整的使用者信息存入 redis
        String redisKey = "login:" + user.getId();
        redisCache.setCacheObject(redisKey, loginUser);

        UserLoginResponse userLoginResponse = new UserLoginResponse(user, jwt);
        return userLoginResponse;
    }

    @Override
    public void logout() {
        // 取得 SecurityContextHolder 中的 userId
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String redisKey = "login:" + loginUser.getUser().getId();
        // 刪除 redis 中的值
        redisCache.deleteObject(redisKey);
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
