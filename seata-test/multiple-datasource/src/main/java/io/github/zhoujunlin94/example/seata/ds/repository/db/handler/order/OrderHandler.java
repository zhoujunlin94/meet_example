package io.github.zhoujunlin94.example.seata.ds.repository.db.handler.order;

import io.github.zhoujunlin94.example.seata.ds.config.OrderMybatisConfig;
import io.github.zhoujunlin94.example.seata.ds.repository.db.entity.order.Order;
import io.github.zhoujunlin94.example.seata.ds.repository.db.handler.account.AccountHandler;
import io.github.zhoujunlin94.example.seata.ds.repository.db.handler.product.ProductHandler;
import io.github.zhoujunlin94.example.seata.ds.repository.db.mapper.order.OrderMapper;
import io.github.zhoujunlin94.meet.tk_mybatis.handler.TKHandler;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoujunlin
 * @date 2023年09月21日 22:39
 * @desc
 */
@Slf4j
@Repository
public class OrderHandler extends TKHandler<OrderMapper, Order> {

    @Resource
    private ProductHandler productHandler;
    @Resource
    private AccountHandler accountHandler;

    @GlobalTransactional
    @Transactional(transactionManager = OrderMybatisConfig.TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public Integer createOrder(Integer userId, Integer productId, Integer price) throws Exception {
        // 购买数量，暂时设置为 1。
        Integer amount = 1;
        log.info("[createOrder] 当前 XID: {}", RootContext.getXID());

        // 扣减库存
        productHandler.reduceStock(productId, amount);

        // 扣减余额
        accountHandler.reduceBalance(userId, price);

        // 保存订单
        Order order = Order.builder().userId(userId).productId(productId).payAmount((long) amount * price).build();
        this.insertSelective(order);
        log.info("[createOrder] 保存订单: {}", order.getId());

        // 返回订单编号
        return order.getId();
    }


}
