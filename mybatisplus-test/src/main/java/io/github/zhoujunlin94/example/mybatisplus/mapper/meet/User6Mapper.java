package io.github.zhoujunlin94.example.mybatisplus.mapper.meet;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User6;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhoujunlin
 * @date 2023年05月06日 16:03
 * @desc
 */
@Mapper
public interface User6Mapper extends BaseMapper<User6> {

    User6 selectLinkById(Long id);

}
