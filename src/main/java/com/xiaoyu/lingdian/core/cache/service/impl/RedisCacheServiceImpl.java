package com.xiaoyu.lingdian.core.cache.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import com.xiaoyu.lingdian.core.cache.model.RedisKeyAndValue;
import com.xiaoyu.lingdian.core.cache.service.RedisCacheService;

public class RedisCacheServiceImpl implements RedisCacheService {

    private RedisTemplate<String, RedisKeyAndValue> redisTemplate;
    
    public void setRedisTemplate(
			RedisTemplate<String, RedisKeyAndValue> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
     * 添加对象
     */
    @Override
    public boolean add(final RedisKeyAndValue redisKeyAndValue) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] key  = serializer.serialize(redisKeyAndValue.getKey());  
                byte[] name = serializer.serialize(redisKeyAndValue.getValue());
                if (redisKeyAndValue.getExpire() == null || redisKeyAndValue.getExpire() == 0) {
                	return connection.setNX(key, name);
				} else {
					connection.setEx(key, redisKeyAndValue.getExpire(), name);
					return true;
				}
            }  
        });  
        return result;  
    }  

    /**
     * 添加集合
     */
    @Override
    public boolean add(final List<RedisKeyAndValue> list) {
        if (CollectionUtils.isEmpty(list)) {
        	throw new NullPointerException("添加的集合不存在");
		}
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (RedisKeyAndValue redisKeyAndValue : list) {   
                    byte[] key  = serializer.serialize(redisKeyAndValue.getKey());  
                    byte[] name = serializer.serialize(redisKeyAndValue.getValue());
                    if (redisKeyAndValue.getExpire() == null || redisKeyAndValue.getExpire() == 0) {
                    	connection.setNX(key, name);
    				} else {
    					connection.setEx(key, redisKeyAndValue.getExpire(), name);
    				}                    
                }  
                return true;  
            }  
        }, false, true);  
        return result; 
    }  
    
    /**
     * 删除对象 ,依赖key
     */
    @Override
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);
    }  
  
    /**
     * 删除集合 ,依赖key集合
     */
    @Override
    public void delete(List<String> keys) {  
        redisTemplate.delete(keys);  
    }  
    
    /**
     * 修改对象 
     */
    @Override
    public boolean update(final RedisKeyAndValue redisKeyAndValue) {  
        String key = redisKeyAndValue.getKey();  
        if (get(key) == null) {  
            throw new NullPointerException("数据行不存在, key = " + key);  
        }  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();    
                byte[] key  = serializer.serialize(redisKeyAndValue.getKey());  
                byte[] name = serializer.serialize(redisKeyAndValue.getValue());
                if (redisKeyAndValue.getExpire() == null || redisKeyAndValue.getExpire() == 0) {
                	connection.setNX(key, name);
				} else {
					connection.setEx(key, redisKeyAndValue.getExpire(), name);
				}  
                return true;  
            }  
        });  
        return result;  
    }  
    
    /**
     * 根据key获取对象
     */
    @Override
    public RedisKeyAndValue get(final String key) {  
    	RedisKeyAndValue result = redisTemplate.execute(new RedisCallback<RedisKeyAndValue>() {  
            public RedisKeyAndValue doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value = connection.get(serializer.serialize(key));  
                if (value == null) {  
                    return null;  
                } 
                return new RedisKeyAndValue(key, serializer.deserialize(value));  
            }  
        });  
        return result;  
    }  

}
