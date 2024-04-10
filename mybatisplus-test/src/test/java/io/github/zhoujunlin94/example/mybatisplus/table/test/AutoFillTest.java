package io.github.zhoujunlin94.example.mybatisplus.table.test;

import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.User5Mapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User5;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 自动填充测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class AutoFillTest {

    @Resource
    private User5Mapper user5Mapper;

    @Test
    public void test() {
        User5 user = new User5(null, "Tom", 1, "tom@qq.com", null);
        user5Mapper.insert(user);
        log.info("query user:{}", user5Mapper.selectById(user.getId()));
        User5 beforeUser = user5Mapper.selectById(1L);
        log.info("before user:{}", beforeUser);
        beforeUser.setAge(12);
        beforeUser.setOperator(null);
        user5Mapper.updateById(beforeUser);
        log.info("query user:{}", user5Mapper.selectById(1L));
    }
}
