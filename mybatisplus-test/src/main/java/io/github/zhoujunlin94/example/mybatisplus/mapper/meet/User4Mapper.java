package io.github.zhoujunlin94.example.mybatisplus.mapper.meet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User4;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2023年05月05日 16:00
 * @desc
 */
@Mapper
public interface User4Mapper extends BaseMapper<User4> {

    List<User4> selectUserPage(IPage<User4> page, @Param("ew") QueryWrapper<User4> wrapper);

}
