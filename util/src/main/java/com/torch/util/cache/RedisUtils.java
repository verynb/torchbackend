package com.torch.util.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

  @SuppressWarnings("rawtypes")
  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 批量删除对应的value
   */
  public void remove(final String... keys) {
    for (String key : keys) {
      remove(key);
    }
  }

  /**
   * 批量删除key
   */
  @SuppressWarnings("unchecked")
  public void removePattern(final String pattern) {
    Set<Serializable> keys = redisTemplate.keys(pattern);
    if (keys.size() > 0) {
      redisTemplate.delete(keys);
    }
  }

  /**
   * 删除对应的value
   */
  @SuppressWarnings("unchecked")
  public void remove(final String key) {
    if (exists(key)) {
      redisTemplate.delete(key);
    }
  }

  /**
   * 判断缓存中是否有对应的value
   */
  @SuppressWarnings("unchecked")
  public boolean exists(final String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 读取缓存
   */
  @SuppressWarnings("unchecked")
  public Object get(final String key) {
    Object result = null;
    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
    result = operations.get(key);
    return result;
  }

  /**
   * 写入缓存(不超时)
   *
   * @return boolean
   * @Description TODO
   */
  @SuppressWarnings("unchecked")
  public boolean set(final String key, Object value) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 写入缓存-超时
   *
   * @param expireTime 超时时间  秒
   * @return boolean
   */
  @SuppressWarnings("unchecked")
  public boolean set(final String key, Object value, Long expireTime) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 刷新过期时间
   *
   * @return void
   */
  @SuppressWarnings("unchecked")
  public void refreshExpireTime(String key, Long expireTime) {
    redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
  }

  /**
   * redis对map的操作
   * 1.inintMap
   */
  public void putMap(String key, Map map) {
    redisTemplate.opsForHash().putAll(key, map);
  }

  public Map getMap(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  /**
   * 2.获取map中的value
   */
  public <T> List<T> getMapVlues(String key) {
   return redisTemplate.opsForHash().values(key);
  }

  /**
   * 3.获取map中的一个valus
   */
  public <T> T getMapValue(String key,Object keys){
    return (T)redisTemplate.opsForHash().get(key,keys);
  }

  /**
   * 4设置map中key的一个值
   */
  public void putKeys(String key,Object keys,Object value){
    redisTemplate.opsForHash().put(key,keys,value);
  }

}