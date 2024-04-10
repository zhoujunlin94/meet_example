package io.github.zhoujunlin94.example.seata.product.dto;

import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2023年09月23日 11:00
 * @desc
 */
@Data
public class ProductReduceStockDTO {

    /**
     * 商品编号
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer amount;

}
