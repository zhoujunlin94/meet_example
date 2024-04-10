package io.github.zhoujunlin94.example.seata.account.repository.db.mapper.account;

import io.github.zhoujunlin94.example.seata.account.repository.db.entity.account.Account;
import io.github.zhoujunlin94.meet.tk_mybatis.mapper.TKMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper extends TKMapper<Account> {

    @Update("UPDATE account SET balance = balance - #{price} WHERE id = #{userId,jdbcType=INTEGER} AND balance >= #{price}")
    int reduceBalance(@Param("userId") Integer userId,
                      @Param("price") Integer price);

}