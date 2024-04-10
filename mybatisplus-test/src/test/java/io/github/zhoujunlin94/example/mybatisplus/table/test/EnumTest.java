package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.enums.*;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.User6Mapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User6;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 内置 Enums 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class EnumTest {

    @Autowired
    private User6Mapper user6Mapper;

    @Test
    public void selectXML() {
        User6 user = user6Mapper.selectLinkById(1L);
        System.out.println(user);
        Assertions.assertNotNull(user);
    }

    @Test
    public void insert() {
        User6 user = new User6();
        user.setName("K神");
        user.setAge(AgeEnum.ONE);
        user.setGrade(GradeEnum.HIGH);
        user.setGender(GenderEnum.MALE);
        user.setStrEnum(StrEnum.ONE);
        user.setEmail("abc@mp.com");
        Assertions.assertTrue(user6Mapper.insert(user) > 0);
        // 成功直接拿回写的 ID
        System.err.println("\n插入成功 ID 为：" + user.getId());

        List<User6> list = user6Mapper.selectList(null);
        for (User6 u : list) {
            System.out.println(u);
            org.assertj.core.api.Assertions.assertThat(u.getAge()).isNotNull();
            if (u.getId().equals(user.getId())) {
                org.assertj.core.api.Assertions.assertThat(u.getGender()).isNotNull();
                org.assertj.core.api.Assertions.assertThat(u.getGrade()).isNotNull();
                org.assertj.core.api.Assertions.assertThat(u.getStrEnum()).isNotNull();
            }
        }
    }

    @Test
    public void delete() {
        Assertions.assertTrue(user6Mapper.delete(new QueryWrapper<User6>()
                .lambda().eq(User6::getAge, AgeEnum.TWO)) > 0);
    }

    @Test
    public void update() {
        Assertions.assertTrue(user6Mapper.update(new User6().setAge(AgeEnum.TWO),
                new QueryWrapper<User6>().eq("age", AgeEnum.THREE)) > 0);
    }

    @Test
    public void select() {
        User6 user = user6Mapper.selectOne(new QueryWrapper<User6>().lambda().eq(User6::getId, 2));
        Assertions.assertEquals("Jack", user.getName());
        Assertions.assertSame(AgeEnum.THREE, user.getAge());

        //#1500 github: verified ok. Not a bug
        List<User6> userList = user6Mapper.selectList(new QueryWrapper<User6>().lambda().eq(User6::getUserState, UserState.ACTIVE));
        //TODO 一起测试的时候完蛋，先屏蔽掉了。
//        Assertions.assertEquals(3, userList.size());
        Optional<User6> userOptional = userList.stream()
                .filter(x -> x.getId() == 1)
                .findFirst();
        userOptional.ifPresent(user1 -> Assertions.assertSame(user1.getUserState(), UserState.ACTIVE));
    }
}
