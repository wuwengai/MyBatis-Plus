package com.example.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.example.mybatisplus.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("user") //设置实体类对应的表名
public class User {

    //将属性的字段设置为主键
    //@TableId注解的value属性用于指定主键的字段
    //@TableId注解type属性用于设置主键生成策略，如果手动设置了主键,就不会采用主键生成策略
    @TableId(value = "uid",type = IdType.AUTO)
    private Long id;

    //设置属性所对应的字段名
    @TableField(value = "username")
    private String name;

    @TableField("sex")
    private SexEnum sexEnum;

    private Integer age;

    private String email;

    @TableLogic //逻辑删除
    private Integer isDelete;

}
