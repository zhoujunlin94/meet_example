package io.github.zhoujunlin94.example.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhoujunlin94.example.mybatisplus.handler.base.CacheCfgHandler;
import io.github.zhoujunlin94.example.mybatisplus.handler.meet.MeetUserHandler;
import io.github.zhoujunlin94.example.mybatisplus.model.base.CacheCfg;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.MeetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 22:07
 * @desc
 */
@Slf4j
@RestController
public class TableController {

    @Resource
    private MeetUserHandler meetUserHandler;

    @Resource
    private CacheCfgHandler cacheCfgHandler;

    @GetMapping("insertUser")
    public boolean insertUser(@RequestParam Integer userId, @RequestParam String userName) {
        MeetUser meetUser = MeetUser.builder().userId(userId).userName(userName).build();
        boolean save = meetUserHandler.save(meetUser);
        log.warn("id:{}", meetUser.getId());
        return save;
    }

    @GetMapping("insertCache")
    public boolean insertCache(@RequestParam String key, @RequestParam String value, @RequestParam String desc) {
        CacheCfg cacheCfg = CacheCfg.builder().key(key).value(value).desc(desc).build();
        return cacheCfgHandler.save(cacheCfg);
    }

    @GetMapping("deleteCache")
    public boolean deleteCache(String key) {
        return cacheCfgHandler.removeById(key);
    }

    @GetMapping("updateCache")
    public boolean updateCache(@RequestParam String key, @RequestParam String value) {
        CacheCfg cacheCfg = CacheCfg.builder().key(key).value(value).build();
        return cacheCfgHandler.updateById(cacheCfg);
    }

    @GetMapping("getCache")
    public CacheCfg getCache(@RequestParam String key) {
        return cacheCfgHandler.getById(key);
    }

    @GetMapping("getFromCache")
    public String getFromCache(String key) {
        return cacheCfgHandler.selectFromCache(key);
    }

    @GetMapping("listCache")
    public Page<CacheCfg> listCache(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam(required = false) String key) {
        return cacheCfgHandler.selectPageLikeKey(pageNo, pageSize, key);
    }

}
