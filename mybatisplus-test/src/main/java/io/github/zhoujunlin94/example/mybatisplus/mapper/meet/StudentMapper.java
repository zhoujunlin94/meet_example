package io.github.zhoujunlin94.example.mybatisplus.mapper.meet;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生Mapper层
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {


}
