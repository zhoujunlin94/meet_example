package io.github.zhoujunlin94.example.mybatisplus.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.base.Man;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManMapper extends BaseMapper<Man> {

    Man selectLinkById(Long id);
}
