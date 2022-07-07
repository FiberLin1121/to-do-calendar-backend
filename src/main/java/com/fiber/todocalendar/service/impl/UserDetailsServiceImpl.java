package com.fiber.todocalendar.service.impl;

import com.fiber.todocalendar.dao.UserDao;
import com.fiber.todocalendar.exception.AccountNotFoundException;
import com.fiber.todocalendar.model.LoginUser;
import com.fiber.todocalendar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查詢使用者資料
        User user = userDao.getUserByEmail(username);

        // 如果使用者不存在就拋出異常
        if (Objects.isNull(user)) {
            throw new AccountNotFoundException("帳號或密碼錯誤");
        }

        // 查詢對應的權限訊息
        List<String> list = new ArrayList<>(Arrays.asList("user"));

        // 把資料封裝成 UserDetails 返回
        return new LoginUser(user, list);
    }
}
