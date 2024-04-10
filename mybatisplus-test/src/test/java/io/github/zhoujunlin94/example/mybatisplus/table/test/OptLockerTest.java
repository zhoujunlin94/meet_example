package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.User3Mapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User3;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 乐观锁
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class OptLockerTest {

    @Resource
    User3Mapper user3Mapper;

    @Test
    public void testUpdateByIdSucc() {
        User3 user = new User3();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        user3Mapper.insert(user);
        Long id = user.getId();

        User3 userUpdate = new User3();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(1);
        // UPDATE user3 SET age=19, version=2 WHERE id=1654327497027239937 AND version=1
        assertThat(user3Mapper.updateById(userUpdate)).isEqualTo(1);
        assertThat(userUpdate.getVersion()).isEqualTo(2);
    }

    @Test
    public void testUpdateByIdSuccFromDb() {
        User3 user = user3Mapper.selectById(1);
        int oldVersion = user.getVersion();
        // 更新之后  会将version更新到user对象
        int i = user3Mapper.updateById(user);
        assertThat(i).isEqualTo(1);
        assertThat(oldVersion + 1).isEqualTo(user.getVersion());
    }

    @Test
    public void testUpdateByIdFail() {
        User3 user = new User3();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        user3Mapper.insert(user);
        Long id = user.getId();

        User3 userUpdate = new User3();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(0);
        // Should update failed due to incorrect version(actually 1, but 0 passed in)
        Assertions.assertEquals(0, user3Mapper.updateById(userUpdate));
    }

    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        User3 user = new User3();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        user3Mapper.insert(user);
        Long id = user.getId();

        User3 userUpdate = new User3();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(null);
        // Should update success as no version passed in
        Assertions.assertEquals(1, user3Mapper.updateById(userUpdate));
        User3 updated = user3Mapper.selectById(id);
        // Version not changed
        Assertions.assertEquals(1, updated.getVersion().intValue());
        // Age updated
        Assertions.assertEquals(19, updated.getAge().intValue());
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * update(et,ew) et:必须带上version的值才会触发乐观锁
     */
    @Test
    public void testUpdateByEntitySucc() {
        QueryWrapper<User3> ew = new QueryWrapper<>();
        ew.eq("version", 1);
        long count = user3Mapper.selectCount(ew);

        User3 entity = new User3();
        entity.setAge(28);
        entity.setVersion(1);

        // updated records should be same
        Assertions.assertEquals(count, user3Mapper.update(entity, null));
        ew = new QueryWrapper<>();
        ew.eq("version", 1);
        // No records found with version=1
        Assertions.assertEquals(0, user3Mapper.selectCount(ew).intValue());
        ew = new QueryWrapper<>();
        ew.eq("version", 2);
        // All records with version=1 should be updated to version=2
        Assertions.assertEquals(count, user3Mapper.selectCount(ew).intValue());
    }

}
