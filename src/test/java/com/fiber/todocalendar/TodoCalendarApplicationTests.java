package com.fiber.todocalendar;

import com.fiber.todocalendar.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class TodoCalendarApplicationTests {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test() throws Exception {
//        String pwd = passwordEncoder.encode("wowo1121");
//        System.out.println("pwd = " + pwd);
//        System.out.println("matches = " + passwordEncoder.matches("wowo1121", "$2a$10$4pFA2eelvb3zzn8mpj03nurNnOY.edswVHk6EJ0ja8Av2oGcPRUcu"));
//
//        String token = JwtUtil.createJWT("wowo");
//        System.out.println("token = " + token);
        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyN2FhODYzZjZjYTk0MTY2YWM1NjVjNTk5MWM5N2VhZSIsInN1YiI6IjYyYjEzNjFkNTIxZDY3Nzk0MTQwODIzNyIsImlzcyI6InRvZG9DYWxlbmRhciIsImlhdCI6MTY1NjUyMjYwMiwiZXhwIjoxNjU2NTI2MjAyfQ.vu4bh6ifH3acFPCfdJv5kHQWfJMMBaIfnxsfvjRuofY");
        System.out.println("claims = " + claims);
        System.out.println("subject = " + claims.getSubject());

        redisTemplate.opsForValue().set("springboot", "test");

    }
}
