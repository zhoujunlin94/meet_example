package io.github.zhoujunlin94.example.mybatisplus.mapper.meet;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.MeetUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetUserMapper extends BaseMapper<MeetUser> {
}