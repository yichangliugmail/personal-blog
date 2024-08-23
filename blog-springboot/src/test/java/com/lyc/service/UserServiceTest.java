package com.lyc.service;

import com.lyc.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liuYichang
 * @description
 * @date 2023/4/25 20:10
 */

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testLogin(){
        String mima = SecurityUtil.sha256Encrypt("administrator");
        System.out.println(mima+"++ddddddddddddddd");
    }
}
