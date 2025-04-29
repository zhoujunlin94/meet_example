package io.github.zhoujunlin94.example.jpa;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONArray;
import io.github.zhoujunlin94.example.jpa.model.meet.MeetUser;
import io.github.zhoujunlin94.example.jpa.repository.meet.MeetUserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2023年04月23日 16:06
 * @desc
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = JPATestApplication.class)
public class MeetUserJpaTest {

    @Resource
    MeetUserRepository meetUserRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void select() {
        MeetUser meetUser = meetUserRepository.findFirstByUserId(111);
        log.info(meetUser.toString());
    }

    @Test
    public void save() {
        MeetUser meetUser = new MeetUser(null, 3456, "hahaha");
        log.info(meetUser.toString());
        MeetUser result = meetUserRepository.save(meetUser);
        log.info(result.toString());
    }

    @Test
    public void count() {
        long count = meetUserRepository.count(Example.of(new MeetUser().setUserName("hahaha")));
        log.info(count + "");
        count = meetUserRepository.count(Example.of(new MeetUser()));
        log.info(count + "");
    }

    @Test
    public void count2() {
        MeetUser meetUser = new MeetUser(4L, 3456, "hahaha");
        Example<MeetUser> example = Example.of(meetUser,
                ExampleMatcher.matching()
                        // 忽略字段
                        .withIgnorePaths("id", "userId")
                        .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.startsWith()));
        List<MeetUser> users = meetUserRepository.findAll(example);
        log.info(JSONArray.toJSONString(users));
    }

    @Test
    public void deleteById() {
        meetUserRepository.deleteById(1L);
    }

    @Test
    public void updateByUserId() {
        int rows = meetUserRepository.updateByUserId("jjjjjjjj", 3456);
        log.info(rows + "");
    }

    @Test
    public void page() {
        Sort sort = Sort.by(CollUtil.newArrayList(new Sort.Order(Sort.Direction.ASC, "userId"),
                new Sort.Order(Sort.Direction.DESC, "userName")));
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<MeetUser> meetUsers = meetUserRepository.findAll(pageRequest);
        log.info(JSONArray.toJSONString(meetUsers.getContent()));
    }

}
