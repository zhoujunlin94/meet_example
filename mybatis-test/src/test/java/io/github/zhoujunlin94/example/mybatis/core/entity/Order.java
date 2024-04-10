package io.github.zhoujunlin94.example.mybatis.core.entity;

import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2024年04月07日 17:26
 * @desc
 */
@Data
public class Order {

    private Integer id;
    private Integer userId;
    private String orderToken;

}
