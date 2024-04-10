package io.github.zhoujunlin94.example.seata.account.dto;

import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2023年09月23日 11:24
 * @desc
 */
@Data
public class AccountReduceBalanceDTO {

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 扣减金额
     */
    private Integer price;

}
