package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.UserMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author sundongkai
 * @since 2021-02-04
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class UpdateWrapperTest {

    @Resource
    private UserMapper userMapper;

    /**
     * UPDATE user SET age=?, email=? WHERE (name = ?)
     */
    @Test
    public void tests() {

        //方式一：
        User user = new User();
        user.setAge(29);
        user.setEmail("test3update@baomidou.com");

        userMapper.update(user, new UpdateWrapper<User>().eq("name", "Tom"));

        //方式二：
        //不创建User对象
        userMapper.update(null, new UpdateWrapper<User>()
                .set("age", 29).set("email", "test3update@baomidou.com").eq("name", "Tom"));

    }

    /**
     * 使用lambda条件构造器
     * UPDATE user SET age=?, email=? WHERE (name = ?)
     */
    @Test
    public void testLambda() {

        //方式一：
        User user = new User();
        user.setAge(29);
        user.setEmail("test3update@baomidou.com");

        userMapper.update(user, new LambdaUpdateWrapper<User>().eq(User::getName, "Tom"));

        //方式二：
        //不创建User对象
        userMapper.update(null, new LambdaUpdateWrapper<User>()
                .set(User::getAge, 29).set(User::getEmail, "test3update@baomidou.com").eq(User::getName, "Tom"));

    }


}
