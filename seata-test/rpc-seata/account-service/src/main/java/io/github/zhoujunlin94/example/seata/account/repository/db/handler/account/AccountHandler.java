package io.github.zhoujunlin94.example.seata.account.repository.db.handler.account;

import cn.hutool.core.convert.Convert;
import io.github.zhoujunlin94.example.seata.account.config.AccountMybatisConfig;
import io.github.zhoujunlin94.example.seata.account.repository.db.entity.account.Account;
import io.github.zhoujunlin94.example.seata.account.repository.db.mapper.account.AccountMapper;
import io.github.zhoujunlin94.meet.tk_mybatis.handler.TKHandler;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author zhoujunlin
 * @date 2023年09月21日 22:38
 * @desc
 */
@Slf4j
@Repository
public class AccountHandler extends TKHandler<AccountMapper, Account> {

    // 开启新事物
    @Transactional(transactionManager = AccountMybatisConfig.TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void reduceBalance(Integer userId, Integer price) throws Exception {
        log.info("[reduceBalance] 当前 XID: {}", RootContext.getXID());

        // 检查余额
        checkBalance(userId, price);

        log.info("[reduceBalance] 开始扣减用户 {} 余额", userId);
        // 扣除余额
        int updateCount = this.baseMapper.reduceBalance(userId, price);

        if (updateCount == 0) {
            log.warn("[reduceBalance] 扣除用户 {} 余额失败", userId);
            throw new Exception("余额不足");
        }
        // 扣除成功
        log.info("[reduceBalance] 扣除用户 {} 余额成功", userId);
    }

    private void checkBalance(Integer userId, Integer price) throws Exception {
        log.info("[checkBalance] 检查用户 {} 余额", userId);
        Integer balance = Optional.ofNullable(selectByPrimaryKey(userId)).map(Account::getBalance).map(Convert::toInt).orElse(0);
        if (balance < price) {
            log.warn("[checkBalance] 用户 {} 余额不足，当前余额:{}", userId, balance);
            throw new Exception("余额不足");
        }
    }

}
