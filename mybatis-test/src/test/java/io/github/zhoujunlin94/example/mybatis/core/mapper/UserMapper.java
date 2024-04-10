package io.github.zhoujunlin94.example.mybatis.core.mapper;

import io.github.zhoujunlin94.example.mybatis.core.entity.User;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/4/6 22:28
 * @desc
 */
public interface UserMapper {

    int insertUser();

    List<User> selectAll();

    List<User> selectUserAndOrders();

}
