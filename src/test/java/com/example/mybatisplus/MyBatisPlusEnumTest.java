package com.example.mybatisplus;

import com.example.mybatisplus.enums.SexEnum;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusEnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        User user = new User();
        user.setName("admin");
        user.setAge(33);
        user.setSexEnum(SexEnum.MALE);
        int result = userMapper.insert(user);
        System.out.println("result:"+result);
    }
}
