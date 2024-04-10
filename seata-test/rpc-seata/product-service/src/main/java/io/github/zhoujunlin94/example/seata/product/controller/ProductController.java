package io.github.zhoujunlin94.example.seata.product.controller;

import io.github.zhoujunlin94.example.seata.product.dto.ProductReduceStockDTO;
import io.github.zhoujunlin94.example.seata.product.repository.db.handler.product.ProductHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductHandler productHandler;

    @PostMapping("/reduceStock")
    public boolean reduceStock(@RequestBody ProductReduceStockDTO reduceStockDTO) {
        log.info("[reduceStock] 收到减少库存请求, 商品:{}, 数量:{}", reduceStockDTO.getProductId(), reduceStockDTO.getAmount());
        try {
            productHandler.reduceStock(reduceStockDTO.getProductId(), reduceStockDTO.getAmount());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
