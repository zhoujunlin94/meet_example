package io.github.zhoujunlin94.example.seata.order.repository.db.handler.order;

import cn.hutool.core.util.BooleanUtil;
import io.github.zhoujunlin94.example.seata.order.config.OrderMybatisConfig;
import io.github.zhoujunlin94.example.seata.order.repository.db.entity.order.Order;
import io.github.zhoujunlin94.example.seata.order.repository.db.mapper.order.OrderMapper;
import io.github.zhoujunlin94.example.seata.order.repository.feign.AccountClient;
import io.github.zhoujunlin94.example.seata.order.repository.feign.ProductClient;
import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import io.github.zhoujunlin94.meet.tk_mybatis.handler.TKHandler;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年09月21日 22:39
 * @desc
 */
@Slf4j
@Repository
public class OrderHandler extends TKHandler<OrderMapper, Order> {

    @Resource
    private ProductClient productClient;
    @Resource
    private AccountClient accountClient;

    @GlobalTransactional
    @Transactional(transactionManager = OrderMybatisConfig.TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public Integer createOrder(Integer userId, Integer productId, Integer price) throws Exception {
        // 购买数量，暂时设置为 1。
        Integer amount = 1;
        log.info("[createOrder] 当前 XID: {}", RootContext.getXID());

        // 扣减库存
        this.reduceStock(productId, amount);

        // 扣减余额
        this.reduceBalance(userId, price);

        // 保存订单
        Order order = Order.builder().userId(userId).productId(productId).payAmount((long) amount * price).build();
        this.insertSelective(order);
        log.info("[createOrder] 保存订单: {}", order.getId());

        // 返回订单编号
        return order.getId();
    }

    private void reduceStock(Integer productId, Integer amount) {
        // 参数拼接
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("productId", productId);
        requestMap.put("amount", amount);
        // 执行调用
        JsonResponse response = productClient.reduceStock(requestMap);
        Boolean result = (Boolean) response.getData();
        // 解析结果
        if (BooleanUtil.isFalse(result)) {
            throw new RuntimeException("扣除库存失败");
        }
    }

    private void reduceBalance(Integer userId, Integer price) {
        // 参数拼接
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        requestMap.put("price", price);

        // 执行调用
        JsonResponse response = accountClient.reduceBalance(requestMap);
        Boolean result = (Boolean) response.getData();

        // 解析结果
        if (BooleanUtil.isFalse(result)) {
            throw new RuntimeException("扣除余额失败");
        }
    }

}
