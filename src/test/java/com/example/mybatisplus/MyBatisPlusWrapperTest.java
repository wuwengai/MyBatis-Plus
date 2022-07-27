package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01() {
        //查询用户名包含a，年龄在20到30之间，邮箱信息不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", "a")
                .between("age", 20, 30)
                .isNotNull("email");

        //ELECT uid AS id,username AS name,age,email,is_delete FROM user WHERE is_delete=0 AND (username LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test02() {
        //查询用户信息，按照年龄的降序排序，若年龄相同，则按照id号升序排序
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //DESC:降序      ASC:升序
        queryWrapper.orderByDesc("age").orderByAsc("uid");
        //SELECT uid AS id,username AS name,age,email,is_delete FROM user WHERE is_delete=0 ORDER BY age DESC,uid ASC
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test03() {
        //删除邮箱地址为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println(result);
    }

    @Test
    public void test04() {
        //将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.gt("age", 20)
                .like("username", "a")
                .or()
                .isNull("email");
        User user = new User();
        user.setName("小明");
        user.setEmail("xxx.@qq.com");
        //UPDATE user SET username=?, email=? WHERE is_delete=0 AND (age > ? AND username LIKE ? OR email IS NULL)
        int result = userMapper.update(user, updateWrapper);
        System.out.println("result:" + result);
    }

    @Test
    public void test05() {
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //lambda表达式内的逻辑优先运算
        //UPDATE user SET username=?, email=? WHERE is_delete=0 AND (username LIKE ? AND (age > ? OR email IS NULL))
        updateWrapper.like("username", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setName("小红");
        user.setEmail("xxx.@qq.com");
        int result = userMapper.update(user, updateWrapper);
        System.out.println("result:" + result);
    }

    @Test
    public void test06() {
        //查询指定的字段
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("username", "age", "email");

        //SELECT username,age,email FROM user WHERE is_delete=0
        List<Map<String, Object>> mapList = userMapper.selectMaps(queryWrapper);
        mapList.forEach(System.out::println);
    }

    /**
     * 子查询
     */
    @Test
    public void test07() {
        //查询id小于等于100的用户信息
        //SELECT uid AS id,username AS name,age,email,is_delete FROM user WHERE is_delete=0 AND (uid IN (select uid from user where uid <=100))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("uid", "select uid from user where uid <=100");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test08() {
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("username", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        updateWrapper.set("username", "小黑").set("email", "eee@qq.com");

        //UPDATE user SET username=?,email=? WHERE is_delete=0 AND (username LIKE ? AND (age > ? OR email IS NULL))
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result:" + result);
    }

    @Test
    public void test09() {
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            //isNotBlank:判断指定的字符串不为空，不为null，不为空白符
            queryWrapper.like("username", username);
        }
        if (ageBegin != null) {
            queryWrapper.ge("age", ageBegin);
        }
        if (ageEnd != null) {
            queryWrapper.le("age", ageEnd);
        }
        //SELECT uid AS id,username AS name,age,email,is_delete FROM user WHERE is_delete=0 AND (age >= ? AND age <= ?)
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test10() {
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //根据第一个参数的值，判断是否添加该条件
        queryWrapper.like(StringUtils.isNotBlank(username), "username", username)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        //SELECT uid AS id,username AS name,age,email,is_delete FROM user WHERE is_delete=0 AND (username LIKE ? AND age <= ?)
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test11() {
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> userList = userMapper.selectList(queryWrapper);
        //Preparing: SELECT uid AS id,username AS name,age,email,is_delete FROM user WHERE is_delete=0 AND (username LIKE ? AND age <= ?)
        userList.forEach(System.out::println);
    }

    @Test
    public void test12() {
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName, "a")
                .and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail))
                .set(User::getName, "潇潇")
                .set(User::getEmail, "xiaoxiao@qq.com");
        //UPDATE user SET username=?,email=? WHERE is_delete=0 AND (username LIKE ? AND (age > ? OR email IS NULL))
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result:" + result);
    }

}
