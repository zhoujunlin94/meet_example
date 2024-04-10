package io.github.zhoujunlin94.example.mybatis.core.mapper;

import io.github.zhoujunlin94.example.mybatis.core.entity.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/4/7
 * @desc
 */
public interface OrderMapper {

    int insertOrder(Order order);


    // abc数据中存在  而java类中不存在此字段   要如何映射？autoMappingUnknownColumnBehavior
    @Select(value = "select id, user_id, order_token, abc from t_order")
    List<Order> selectAll();

}
