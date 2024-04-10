package io.github.zhoujunlin94.example.mybatisplus.handler.meet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.User4Mapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User4;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2023年05月05日 16:02
 * @desc
 */
@Service
public class User4ServiceImpl extends ServiceImpl<User4Mapper, User4> implements IUser4Service {

    @Override
    public List<User4> selectUserPage(IPage<User4> page, QueryWrapper<User4> wrapper) {
        return baseMapper.selectUserPage(page, wrapper);
    }

}
