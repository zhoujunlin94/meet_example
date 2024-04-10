package io.github.zhoujunlin94.example.mybatis.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.zhoujunlin94.example.mybatis.handler.base.CacheCfgHandler;
import io.github.zhoujunlin94.example.mybatis.handler.meet.MeetUserHandler;
import io.github.zhoujunlin94.example.mybatis.model.base.CacheCfg;
import io.github.zhoujunlin94.example.mybatis.model.meet.MeetUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 22:07
 * @desc
 */
@RestController
public class TableController {

    @Resource
    private MeetUserHandler meetUserHandler;

    @Resource
    private CacheCfgHandler cacheCfgHandler;

    @GetMapping("insertUser")
    public int insertUser(@RequestParam Integer userId, @RequestParam String userName) {
        MeetUser meetUser = MeetUser.builder().userId(userId).userName(userName).build();
        return meetUserHandler.insertSelective(meetUser);
    }

    @GetMapping("insertCache")
    public int insertCache(@RequestParam String key, @RequestParam String value, @RequestParam String desc) {
        CacheCfg cacheCfg = CacheCfg.builder().key(key).value(value).desc(desc).build();
        return cacheCfgHandler.insertSelective(cacheCfg);
    }

    @GetMapping("deleteCache")
    public int deleteCache(String key) {
        return cacheCfgHandler.deleteByPrimaryKey(key);
    }

    @GetMapping("updateCache")
    public int updateCache(@RequestParam String key, @RequestParam String value) {
        CacheCfg cacheCfg = CacheCfg.builder().key(key).value(value).build();
        return cacheCfgHandler.updateByPrimaryKeySelective(cacheCfg);
    }

    @GetMapping("getCache")
    public CacheCfg getCache(@RequestParam String key) {
        return cacheCfgHandler.selectByPrimaryKey(key);
    }

    @GetMapping("getFromCache")
    public String getFromCache(String key) {
        return cacheCfgHandler.selectFromCache(key);
    }

    @GetMapping("listCache")
    public PageInfo<CacheCfg> listCache(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String key) {
        return PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() -> cacheCfgHandler.selectLikeKey(key));
    }

}
