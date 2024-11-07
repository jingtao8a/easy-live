package org.jingtao8a.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component("redisUtils")
public class RedisUtils<V> {
    @Resource
    RedisTemplate<String, V> redisTemplate;

    /***
     * 删除 key
     * @param key
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>)CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取 key 对应的 value
     * @param key
     * @return
     */
    public V get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 加入 key value
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置redisKey:{}, value:{}失败", key, value);
            return false;
        }
    }

    /**
     * 判断是否含有 key
     * @param key
     * @return
     */
    public boolean keyExists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 加入 key value 并设置 超时时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setex(String key, V value, int time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("设置 redisKey:{}, value:{} 失败", key, value);
            return false;
        }
    }

    /**
     * 设置 key 的 过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 key 对应的 列表 中所有的 member
     * @param key
     * @return
     */
    public List<V> getQueueList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 往 key 对应的 列表 左边插入 value， 如果 time >  0, 则更新 key 的生存时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lpush(String key, V value, Long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time != null && time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从 key 对应的列表中 删除值等于 value 的元素 （从左往右第 1 个)
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, 1, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 往 key 对应的 列表 从左批量 插入 values, 如果 time > 0, 则设置 key 的超时时间
     * @param key
     * @param values
     * @param time
     * @return
     */
    public boolean lpushAll(String key, List<V> values, Long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            if (time != null && time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 key 对应的 列表 右侧 弹出一个 value
     * @param key
     * @return
     */
    public V rpop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 key 对应的 value 加 1
     * @param key
     * @return
     */
    public Long increment(String key) {
        Long count = redisTemplate.opsForValue().increment(key, 1);
        return count;
    }

    /**
     * 将 key 对应的 value 加 1 , 如果 value == 1 , 则设置key的过期时间 为 milliseconds
     * @param key
     * @param milliseconds
     * @return
     */
    public Long incrementex(String key, Long milliseconds) {
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            expire(key, milliseconds);
        }
        return count;
    }

    /**
     * 将 key 对应的 value 减 1
     * @param key
     * @return
     */
    public Long decrement(String key) {
        Long count = redisTemplate.opsForValue().decrement(key, 1);
        if (count <= 0) {
            redisTemplate.delete(key);
        }
        log.info("key: {}, 减少数量{}", key, count);
        return count;
    }

    /**
     * 获取 keyPrefix 的 keyList
     * @param keyPrefix
     * @return
     */
    public Set<String> getByKeyPrefix(String keyPrefix) {
        Set<String> keyList  = redisTemplate.keys(keyPrefix + "*");
        return keyList;
    }

    /**
     * 批量获取 keyPrefix 的 key-value
     * @param keyPrefix
     * @return
     */
    public Map<String, V> getBatch(String keyPrefix) {
        Set<String> keySet = getByKeyPrefix(keyPrefix);
        List<String> keyList = new ArrayList<>(keySet);
        List<V> valueList = redisTemplate.opsForValue().multiGet(keyList);

//        Map<String, V> resultMap = keyList.stream().collect(Collectors.toMap(key -> key, key -> valueList.get(keyList.indexOf(key))));
        Map<String, V> resultMap = new HashMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            resultMap.put(keyList.get(i), valueList.get(i));
        }
        return resultMap;
    }

    /**
     * sorted set 的 key-v 的 score 加 1
     * @param key
     * @param v
     */
    public void zaddCount(String key, V v) {
        redisTemplate.opsForZSet().incrementScore(key, v, 1);
    }

    /**
     * 获取sorted set指定区间的元素
     * @param key
     * @param count
     * @return
     */
    public List<V> getZSetList(String key, Integer count) {
        Set<V> topElements = redisTemplate.opsForZSet().reverseRange(key, 0, count);
        List<V> list = new ArrayList<>(topElements);
        return list;
    }
}
