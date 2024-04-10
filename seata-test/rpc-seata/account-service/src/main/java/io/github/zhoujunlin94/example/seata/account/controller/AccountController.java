package io.github.zhoujunlin94.example.seata.account.controller;

import io.github.zhoujunlin94.example.seata.account.dto.AccountReduceBalanceDTO;
import io.github.zhoujunlin94.example.seata.account.repository.db.handler.account.AccountHandler;
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
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountHandler accountHandler;

    @PostMapping("/reduceBalance")
    public Boolean reduceBalance(@RequestBody AccountReduceBalanceDTO accountReduceBalanceDTO) {
        log.info("[reduceBalance] 收到减少余额请求, 用户:{}, 金额:{}", accountReduceBalanceDTO.getUserId(), accountReduceBalanceDTO.getPrice());
        try {
            accountHandler.reduceBalance(accountReduceBalanceDTO.getUserId(), accountReduceBalanceDTO.getPrice());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
