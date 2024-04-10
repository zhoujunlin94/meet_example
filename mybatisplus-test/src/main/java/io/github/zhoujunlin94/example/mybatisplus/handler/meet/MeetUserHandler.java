package io.github.zhoujunlin94.example.mybatisplus.handler.meet;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.MeetUserMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.MeetUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 22:06
 * @desc
 */
@Component
public class MeetUserHandler extends ServiceImpl<MeetUserMapper, MeetUser> {

    @Resource
    private MeetUserMapper meetUserMapper;

}
