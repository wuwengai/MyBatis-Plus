package com.example.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.ProductMapper;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.Product;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testPage() {
        Page<User> page = new Page<>(2, 3);

        userMapper.selectPage(page, null);
        System.out.println(page.getRecords()); //当前页的数据
        System.out.println(page.getCurrent()); //获取当前的页码
        System.out.println(page.getPages());  //总页数
        System.out.println(page.getSize()); //当前页的条数
        System.out.println(page.getTotal());  //总记录数
        System.out.println(page.hasNext()); //是否有下一页
        System.out.println(page.hasPrevious()); //是否有上一页
    }

    @Test
    public void testPageVo() {
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPageVo(page, 20);
        System.out.println(page.getRecords()); //当前页的数据
        System.out.println(page.getCurrent()); //获取当前的页码
        System.out.println(page.getPages());  //总页数
        System.out.println(page.getSize()); //当前页的条数
        System.out.println(page.getTotal());  //总记录数
    }

    @Test
    public void testProduct() {
        Product productLi = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + productLi.getPrice());

        Product productWang = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + productWang.getPrice());

        productLi.setPrice(productLi.getPrice() + 50);
        productMapper.updateById(productLi);

        productWang.setPrice(productWang.getPrice() - 30);
        int result = productMapper.updateById(productWang);
        if(result == 0){
            //操作失败，重试
            Product productNew = productMapper.selectById(1L);
            productNew.setPrice(productNew.getPrice()-30);
            productMapper.updateById(productNew);
        }

        Product p3 = productMapper.selectById(1L);

        System.out.println("最后的结果：" + p3.getPrice());


    }
}
