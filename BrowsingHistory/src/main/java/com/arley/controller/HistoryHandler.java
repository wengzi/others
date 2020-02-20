package com.arley.controller;

import com.arley.constant.SuffixEnum;
import com.arley.model.HistoryPo;
import com.arley.service.RedisService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Arley
 */
@RestController
@RequestMapping("/browsingHistory")
public class HistoryHandler {

    @Autowired
    private RedisService redisService;

    /**
     * 默认过期时长，单位：秒
     */
    private final static long DEFAULT_EXPIRE = 60 * 60 * 24 * 30;

    public static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 存一个浏览商品信息
     *
     * @param historyPo
     */
    @PostMapping("/set")
    public void set(@Validated @RequestBody HistoryPo historyPo) {
        String key = getKey(historyPo.getCode(), historyPo.getUserId());
        /* 为了保证浏览商品的 唯一性,每次添加前,将list中该商品ID去掉,再加入,以保证其浏览的最新的商品在最前面 */
        redisService.lrem(key, 1L, historyPo);
        /* 将value push 到该key下的list中 */
        redisService.lpush(key, historyPo);
        /* 浏览记录存5条,五条以后切掉*/
        redisService.lTrim(key, 0L, 4L);
        /* 缓存时间为一个月 */
        redisService.expire(key, DEFAULT_EXPIRE);
    }

    /**
     * 获取当前用户的浏览历史
     *
     * @param code
     * @param userId
     * @return
     */
    @GetMapping("/all/{code}/{userId}")
    public List<HistoryPo> query(@PathVariable("code") Integer code, @PathVariable("userId") Long userId) {
        String key = getKey(code, userId);
        //这里可支持分页 start and end
        return redisService.lrange(key, 0L, 9999L);

        /* 以下代码解决json反序列化之后集合不能操作问题(例如使用Stream)
        String key = getKey(code, userId);
        List<HistoryPo> lrange = redisService.lrange(key, 0L, 9999L);
        JavaType javaType = getCollectionType(LinkedList.class, HistoryPo.class);
        List<HistoryPo> lst = null;
        try {
            lst = mapper.readValue(lrange.toString(), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * 删除单个商品浏览历史
     *
     * @param historyPo
     * @return
     */
    @DeleteMapping("/del")
    public Long del(@Validated @RequestBody HistoryPo historyPo) {
        String key = getKey(historyPo.getCode(), historyPo.getUserId());
        return redisService.lrem(key, 0L, historyPo);
    }

    /**
     * 删除所有商品浏览历史
     *
     * @param code
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/delAll/{code}/{userId}")
    public Long delAll(@PathVariable("code") Integer code, @PathVariable("userId") Long userId) {
        String key = getKey(code, userId);
        return redisService.del(key);
    }

    /**
     * 获取当前用户的key,
     *
     * @param code   区分pc和平台端
     * @param userId 用户id
     * @return userId + pc or mobile
     */
    private String getKey(Integer code, Long userId) {
        String key = userId.toString();
        return StringUtils.join(key, SuffixEnum.getSuffix(code));
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
