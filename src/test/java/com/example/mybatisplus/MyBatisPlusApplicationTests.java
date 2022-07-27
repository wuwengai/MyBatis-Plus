package com.example.mybatisplus;

import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MyBatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("李四");
        user.setAge(23);
        user.setEmail("lisi@qq.com");
        int insert = userMapper.insert(user);
        System.out.println(insert > 0 ? "添加成功" : "添加失败");
        System.out.println(user.getId());
    }

    @Test
    public void testDeleteById() {
        //通过id删除用户信息
//        int result = userMapper.deleteById(1529063111887876098L);
//        System.out.println(result > 0 ? "删除成功" : "删除失败");

        //根据Map集合中所设置的条件删除用户信息  DELETE FROM user WHERE name = ? AND age = ?
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "张三");
//        map.put("age", 23);
//        int result = userMapper.deleteByMap(map);

        //通过多个id实现批量删除
        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(list);

        System.out.println(result > 0 ? "删除成功" : "删除失败");
    }

    @Test
    public void testUpDate() {
        //UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
        user.setId(4L);
        user.setName("李四");
        user.setEmail("lisi@qq.com");
        int result = userMapper.updateById(user);

        System.out.println(result > 0 ? "修改成功" : "修改失败");
    }

    @Test
    public void testSelect(){
        //通过id查询用户信息  SELECT id,name,age,email FROM user WHERE id=?
//        User user = userMapper.selectById(1L);
//        System.out.println(user);

        //根据多个id查询多个用户信息   SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
//        List<Long> list = Arrays.asList(1L, 2L, 3L);
//        List<User> userList = userMapper.selectBatchIds(list);
//        userList.forEach(System.out::println);

        //根据Map集合中的条件查询用户信息
        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
//        Map<String, Object> map = new HashMap<>();
//        map.put("username", "Jack");
//        map.put("age", 20);
//        List<User> users = userMapper.selectByMap(map);
//        users.forEach(System.out::println);

        //查询所有数据
        // SELECT id,name,age,email FROM user
//        List<User> users = userMapper.selectList(null);
//        users.forEach(System.out::println);

        //自定义查询方法
        Map<String, Object> map = userMapper.selectMapById(1L);
        System.out.println(map);

    }


}
