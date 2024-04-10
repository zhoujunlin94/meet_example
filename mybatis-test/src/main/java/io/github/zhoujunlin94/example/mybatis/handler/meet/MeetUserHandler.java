package io.github.zhoujunlin94.example.mybatis.handler.meet;

import io.github.zhoujunlin94.example.mybatis.mapper.meet.MeetUserMapper;
import io.github.zhoujunlin94.example.mybatis.model.meet.MeetUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 22:06
 * @desc
 */
@Component
public class MeetUserHandler {

    @Resource
    private MeetUserMapper meetUserMapper;

    public int insertSelective(MeetUser meetUser) {
        return meetUserMapper.insertSelective(meetUser);
    }

}
