package io.github.zhoujunlin94.example.mybatisplus.handler.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.zhoujunlin94.example.mybatisplus.mapper.base.CacheCfgMapper;
import io.github.zhoujunlin94.example.mybatisplus.model.base.CacheCfg;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 21:55
 * @desc
 */
@Component
public class CacheCfgHandler extends ServiceImpl<CacheCfgMapper, CacheCfg> implements InitializingBean {

    private static final Cache<String, String> CACHE = CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .initialCapacity(500).maximumSize(1000).build();

    @Resource
    private CacheCfgMapper cacheCfgMapper;

    public String selectFromCache(String key) {
        try {
            // 从缓存中获取  如果缓存中没有(真没有或者已经过期或者手动移除) 则从数据库中获取并放入缓存
            return CACHE.get(key, () -> Optional.ofNullable(super.getById(key)).map(CacheCfg::getValue).orElse(StrUtil.EMPTY));
        } catch (Exception e) {
            return StrUtil.EMPTY;
        }
    }

    public Page<CacheCfg> selectPageLikeKey(Integer pageNo, Integer pageSize, String key) {
        LambdaQueryWrapper<CacheCfg> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(key)) {
            lambdaQueryWrapper.like(CacheCfg::getKey, key);
        }
        lambdaQueryWrapper.orderByDesc(CacheCfg::getKey);
        return cacheCfgMapper.selectPage(new Page<>(pageNo, pageSize), lambdaQueryWrapper);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 项目启动时获取所有配置并存入缓存
         * 后期如果更新了数据库  这里也需要同步更新缓存
         * 方案1: 更新数据库的同时  编码更新缓存  只能更新单机   不推荐
         * 方案2: 更新时发送消息队列 同步所有机器缓存更新
         * 方案3: canal监听数据库  通过消息队列  同步所有机器缓存更新
         */
//        Map<String, String> keyValueMap = super.list().stream().collect(Collectors.toMap(CacheCfg::getKey, CacheCfg::getValue));
//        CACHE.putAll(keyValueMap);
    }

}
