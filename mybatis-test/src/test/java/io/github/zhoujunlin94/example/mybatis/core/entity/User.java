package io.github.zhoujunlin94.example.mybatis.core.entity;

import io.github.zhoujunlin94.example.mybatis.core.enums.Sex;
import lombok.Data;

import java.util.List;

@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private Sex sex;

    private String email;

    private List<Order> orders;

}

