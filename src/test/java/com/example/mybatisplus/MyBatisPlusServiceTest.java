package com.example.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.pojo.User;
import com.example.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCount() {
        long count = userService.count();
        System.out.println("总记录数：" + count);
    }

    @Test
    public void testBatch() {
        //批量添加
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("www" + i);
            user.setAge(20 + i);
            user.setEmail(UUID.randomUUID().toString().substring(0, 6) + "@qq.com");
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
    }

    @Test
    public void testGetUser() {
        //根据id查询
        User user = userService.getById(1L);
        Page<User> page = userService.page(new Page<>(1, 3));
        Page<Map<String, Object>> mapPage = userService.pageMaps(new Page<>(1, 3));
        System.out.println(page.getRecords());
        //  System.out.println(user);
        //System.out.println(mapPage.getRecords());
        /*
        [{name=小明, id=4, age=21, email=xxx.@qq.com, is_delete=0},
        {name=Billie, id=5, age=24, email=test5@baomidou.com, is_delete=0},
         {name=www1, id=1529119188385951746, age=21, email=381dbf@qq.com, is_delete=0}]
         */

    }
}
