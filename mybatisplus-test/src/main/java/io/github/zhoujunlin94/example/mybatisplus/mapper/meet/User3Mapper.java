package io.github.zhoujunlin94.example.mybatisplus.mapper.meet;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User3;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * MP 支持不需要 UserMapper.xml 测试注解条件
 * </p>
 */
@Mapper
public interface User3Mapper extends BaseMapper<User3> {

}
