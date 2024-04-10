package io.github.zhoujunlin94.example.seata.order.controller;

import io.github.zhoujunlin94.example.seata.order.repository.db.handler.order.OrderHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Validated
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderHandler orderHandler;

    @PostMapping("/create")
    public Integer createOrder(@RequestParam("userId") Integer userId,
                               @RequestParam("productId") Integer productId,
                               @RequestParam("price") Integer price) throws Exception {
        log.info("[createOrder] 收到下单请求,用户:{}, 商品:{}, 价格:{}", userId, productId, price);
        return orderHandler.createOrder(userId, productId, price);
    }

}
