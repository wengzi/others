package com.arley.service.impl;

import com.arley.model.HistoryPo;
import com.arley.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Arley
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, HistoryPo> redisTemplate;

//    @Resource
//    private ValueOperations<String, Object> valueOperations;
//
//    @Resource
//    private ListOperations<String, Object> listOperations;

    public Long lrem(String key, Long count, HistoryPo value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public long lpush(String key, HistoryPo value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    public void lTrim(String key, Long start, Long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public boolean expire(String key, Long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public List<HistoryPo> lrange(String key, Long start, Long end) {
        List<HistoryPo> range = redisTemplate.opsForList().range(key, start, end);
        return range;
    }

    public Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Long del(String... keys) {
        Set<String> keySet = Stream.of(keys).collect(Collectors.toSet());
        return redisTemplate.delete(keySet);
    }
}
