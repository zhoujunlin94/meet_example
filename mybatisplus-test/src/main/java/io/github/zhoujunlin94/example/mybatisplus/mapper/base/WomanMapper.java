package io.github.zhoujunlin94.example.mybatisplus.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.base.Woman;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WomanMapper extends BaseMapper<Woman> {

    Woman selectLinkById(Long id);
}
