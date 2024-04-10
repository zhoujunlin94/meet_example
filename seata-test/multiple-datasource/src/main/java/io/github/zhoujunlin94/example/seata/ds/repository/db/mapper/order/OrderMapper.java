package io.github.zhoujunlin94.example.seata.ds.repository.db.mapper.order;

import io.github.zhoujunlin94.example.seata.ds.repository.db.entity.order.Order;
import io.github.zhoujunlin94.meet.tk_mybatis.mapper.TKMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends TKMapper<Order> {
}