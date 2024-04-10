package io.github.zhoujunlin94.example.mybatisplus.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.base.CacheCfg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CacheCfgMapper extends BaseMapper<CacheCfg> {
}