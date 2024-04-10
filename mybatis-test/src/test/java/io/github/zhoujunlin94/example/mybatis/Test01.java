package io.github.zhoujunlin94.example.mybatis;

import cn.hutool.core.collection.CollUtil;
import io.github.zhoujunlin94.example.mybatis.core.entity.Order;
import io.github.zhoujunlin94.example.mybatis.core.entity.User;
import io.github.zhoujunlin94.example.mybatis.core.mapper.OrderMapper;
import io.github.zhoujunlin94.example.mybatis.core.mapper.UserMapper;
import io.github.zhoujunlin94.example.mybatis.util.SqlSessionUtil;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/4/6 22:31
 * @desc
 */
public class Test01 {

    @SneakyThrows
    @Test
    public void test01() {
        UserMapper userMapper = SqlSessionUtil.getMapper(UserMapper.class);
        userMapper.insertUser();
        // 手动提交事务
        //sqlSession.commit();
    }

    @Test
    public void test02() {
//        namespace+id找到执行的sql 参数只有一个时 直接传参就行了
        User user = SqlSessionUtil.getSqlSession().selectOne("io.github.zhoujunlin94.example.mybatis.mybatis.core.mapper.UserMapper.selectById", 1);
        System.out.println(user);
    }

    @Test
    public void test03() {
        // 测试缓存
        UserMapper userMapper = SqlSessionUtil.getMapper(UserMapper.class);
        List<User> users1 = userMapper.selectAll();
        users1.forEach(System.out::println);
        System.out.println("======>以下从缓存中获取");
        List<User> users2 = userMapper.selectAll();
        users2.forEach(System.out::println);
    }

    @Test
    public void test04() {
        UserMapper userMapper = SqlSessionUtil.getMapper(UserMapper.class);
        List<User> users = userMapper.selectUserAndOrders();
        for (User user : users) {
            System.out.println("user:" + user);
            if (CollUtil.isEmpty(user.getOrders())) {
                continue;
            }
            /* 延迟加载  只有当真正获取时才查询数据库 */
            for (Order order : user.getOrders()) {
                System.out.println("    =>" + order);
            }
        }
    }

    @Test
    public void test05() {
        // 测试自动返回主键
        OrderMapper orderMapper = SqlSessionUtil.getMapper(OrderMapper.class);
        Order order = new Order();
        order.setUserId(2);
        order.setOrderToken("ORDER_eeeee");

        int i = orderMapper.insertOrder(order);
        System.out.println("result:" + i);
        System.out.println("生成的主键：" + order.getId());
    }

    @Test
    public void test06() {
        OrderMapper orderMapper = SqlSessionUtil.getMapper(OrderMapper.class);
        List<Order> orders = orderMapper.selectAll();
        orders.forEach(System.out::println);
    }


    @Test
    public void test07() {
        // 枚举处理器
        UserMapper userMapper = SqlSessionUtil.getMapper(UserMapper.class);
        List<User> orders = userMapper.selectAll();
        orders.forEach(System.out::println);
    }


}
