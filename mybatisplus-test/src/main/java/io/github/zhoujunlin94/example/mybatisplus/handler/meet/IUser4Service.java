package io.github.zhoujunlin94.example.mybatisplus.handler.meet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.User4;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2023年05月05日 16:01
 * @desc
 */
public interface IUser4Service extends IService<User4> {

    List<User4> selectUserPage(IPage<User4> page, QueryWrapper<User4> wrapper);

}
