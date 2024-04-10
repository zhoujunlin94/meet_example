package io.github.zhoujunlin94.example.mybatisplus.table.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhoujunlin94.example.mybatisplus.MyBatisPlusTestApplication;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.CommonMapper;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.Null1Mapper;
import io.github.zhoujunlin94.example.mybatisplus.mapper.meet.Null2Mapper;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.Common;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.Null1;
import io.github.zhoujunlin94.example.mybatisplus.model.meet.Null2;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = MyBatisPlusTestApplication.class)
public class LogicDeleteTest {

    @Resource
    private CommonMapper commonMapper;
    @Resource
    private Null1Mapper null1Mapper;
    @Resource
    private Null2Mapper null2Mapper;

    @Test
    public void tCommon() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Common common = new Common().setName("" + i);
            commonMapper.insert(common);
            ids.add(common.getId());
        }
        log.error("------------------------------------------------deleteById--------------------------------------------------------");
        // UPDATE common SET `deleted`=1 WHERE `id`=1655082594825428993 AND `deleted`=0
        commonMapper.deleteById(ids.remove(0));
        log.error("------------------------------------------------deleteByMap--------------------------------------------------------");
        commonMapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------delete--------------------------------------------------------");
        commonMapper.delete(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        commonMapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------updateById--------------------------------------------------------");
        // UPDATE common SET `name`='老王' WHERE `id`=1655082595874004993 AND `deleted`=0
        commonMapper.updateById(new Common().setId(ids.remove(0)).setName("老王"));
        log.error("------------------------------------------------update--------------------------------------------------------");
        commonMapper.update(new Common().setName("老王"), Wrappers.<Common>update().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectById--------------------------------------------------------");
        commonMapper.selectById(ids.remove(0));
        log.error("------------------------------------------------selectBatchIds--------------------------------------------------------");
        // SELECT `id`,`name`,`deleted` FROM common WHERE `id` IN ( 1655082596264075266 , 1655082596331184130 ) AND `deleted`=0
        commonMapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------selectByMap--------------------------------------------------------");
        commonMapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------selectOne--------------------------------------------------------");
        commonMapper.selectOne(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectCount--------------------------------------------------------");
        commonMapper.selectCount(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectList--------------------------------------------------------");
        commonMapper.selectList(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMaps--------------------------------------------------------");
        commonMapper.selectMaps(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectObjs--------------------------------------------------------");
        commonMapper.selectObjs(Wrappers.<Common>query().select("id").eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectPage--------------------------------------------------------");
        commonMapper.selectPage(new Page<>(), Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMapsPage--------------------------------------------------------");
        commonMapper.selectMapsPage(new Page<>(), Wrappers.<Common>query().eq("id", ids.remove(0)));
    }

    @Test
    public void tNull1() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Null1 null1 = new Null1().setName("" + i).setDeleted(1);
            null1Mapper.insert(null1);
            ids.add(null1.getId());
        }
        log.error("------------------------------------------------deleteById--------------------------------------------------------");
        null1Mapper.deleteById(ids.remove(0));
        log.error("------------------------------------------------deleteByMap--------------------------------------------------------");
        null1Mapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------delete--------------------------------------------------------");
        null1Mapper.delete(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        null1Mapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------updateById--------------------------------------------------------");
        null1Mapper.updateById(new Null1().setId(ids.remove(0)).setName("老王"));
        log.error("------------------------------------------------update--------------------------------------------------------");
        null1Mapper.update(new Null1().setName("老王"), Wrappers.<Null1>update().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectById--------------------------------------------------------");
        null1Mapper.selectById(ids.remove(0));
        log.error("------------------------------------------------selectBatchIds--------------------------------------------------------");
        null1Mapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------selectByMap--------------------------------------------------------");
        null1Mapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------selectOne--------------------------------------------------------");
        null1Mapper.selectOne(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectCount--------------------------------------------------------");
        null1Mapper.selectCount(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectList--------------------------------------------------------");
        null1Mapper.selectList(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMaps--------------------------------------------------------");
        null1Mapper.selectMaps(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectObjs--------------------------------------------------------");
        null1Mapper.selectObjs(Wrappers.<Null1>query().select("id").eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectPage--------------------------------------------------------");
        null1Mapper.selectPage(new Page<>(), Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMapsPage--------------------------------------------------------");
        null1Mapper.selectMapsPage(new Page<>(), Wrappers.<Null1>query().eq("id", ids.remove(0)));
    }

    @Test
    public void tNull2() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Null2 null2 = new Null2().setName("" + i);
            null2Mapper.insert(null2);
            ids.add(null2.getId());
        }
        log.error("------------------------------------------------deleteById--------------------------------------------------------");
        null2Mapper.deleteById(ids.remove(0));
        log.error("------------------------------------------------deleteByMap--------------------------------------------------------");
        null2Mapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------delete--------------------------------------------------------");
        null2Mapper.delete(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        null2Mapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------updateById--------------------------------------------------------");
        null2Mapper.updateById(new Null2().setId(ids.remove(0)).setName("老王"));
        log.error("------------------------------------------------update--------------------------------------------------------");
        null2Mapper.update(new Null2().setName("老王"), Wrappers.<Null2>update().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectById--------------------------------------------------------");
        null2Mapper.selectById(ids.remove(0));
        log.error("------------------------------------------------selectBatchIds--------------------------------------------------------");
        null2Mapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------selectByMap--------------------------------------------------------");
        null2Mapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------selectOne--------------------------------------------------------");
        null2Mapper.selectOne(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectCount--------------------------------------------------------");
        null2Mapper.selectCount(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectList--------------------------------------------------------");
        null2Mapper.selectList(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMaps--------------------------------------------------------");
        null2Mapper.selectMaps(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectObjs--------------------------------------------------------");
        null2Mapper.selectObjs(Wrappers.<Null2>query().select("id").eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectPage--------------------------------------------------------");
        null2Mapper.selectPage(new Page<>(), Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMapsPage--------------------------------------------------------");
        null2Mapper.selectMapsPage(new Page<>(), Wrappers.<Null2>query().eq("id", ids.remove(0)));
    }
}
