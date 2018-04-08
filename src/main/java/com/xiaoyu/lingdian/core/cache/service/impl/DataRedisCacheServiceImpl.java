package com.xiaoyu.lingdian.core.cache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaoyu.lingdian.core.cache.service.DataRedisCacheService;

public class DataRedisCacheServiceImpl extends DefaultCacheServiceImpl implements DataRedisCacheService {

	private static final Logger logger = LoggerFactory.getLogger(DataRedisCacheServiceImpl.class);

	private static Jedis jedis;
	
	private JedisConnectionFactory jedisConnectionFactory;

	public void setJedisConnectionFactory(
			JedisConnectionFactory jedisConnectionFactory) {
		this.jedisConnectionFactory = jedisConnectionFactory;
	}
	
	/**
	 * 获取一个jedis 客户端
	 * 
	 * @return
	 */
	private Jedis getJedis() {
		if (jedis == null) {
			jedis = jedisConnectionFactory.getShardInfo().createResource();
		}
		return jedis;
	}
	
	@Override
	public String getSingleValueByKey(final String key) {
		try {
			return this.getJedis().get(key);
		} catch (Exception e) {
			logger.error("fail to get the string value from redis", e);
			return "";
		}
	}

	@Override
	public void setSingleData(final String key, final Object value, final int seconds) {
		if (value == null) {
			return;
		}
		try {
			this.getJedis().setex(key, seconds, value.toString());
		} catch (Exception e) {
			logger.error("fail to set the String value to redis", e);
		}
		return;
	}

	@Override
	public void setSingleData(final String key, final Object value) {
		if (value == null) {
			return;
		}
		try {
			this.getJedis().set(key, value.toString());
		} catch (Exception e) {
			logger.error("fail to set the String value to redis", e);
		}
	}

	@Override
	public boolean setIfAbsent(final String key, final String value) {
		if (value == null) {
			return false;
		}
		try {
			long result = this.getJedis().setnx(key, value);
			return result == 1 ? true : false;
		} catch (Exception e) {
			logger.error("fail to set the String value to redis", e);
			return false;
		}
	}

	@Override
	public Map<String, String> getHashByKey(final String key) {
		try {
			return this.getJedis().hgetAll(key);
		} catch (Exception e) {
			logger.error("fail to get the hash value from redis", e);
			return null;
		}
	}

	@Override
	public Set<String> getHashKeys(final String key) {
		try {
			return this.getJedis().hkeys(key);
		} catch (Exception e) {
			logger.error("fail to get the hash value from redis", e);
			return null;
		}
	}

	@Override
	public void setHashData(final String key, final Map<String, String> hash) {
		if (hash == null) {
			return;
		}
		try {
			this.getJedis().hmset(key, hash);
		} catch (Exception e) {
			logger.error("fail to set the hash value to redis", e);
		}
	}

	public void setHashData(final String key, final String fieldKey, final String fieldValue) {
		if (key == null) {
			return;
		}
		try {
			this.getJedis().hset(key, fieldKey, fieldValue);
		} catch (Exception e) {
			logger.error("fail to set the String value to specific map in redis", e);
		}
	}

	public String getHashData(final String key, final String fieldKey) {
		if (key == null) {
			return null;
		}
		try {
			return this.getJedis().hget(key, fieldKey);
		} catch (Exception e) {
			logger.error("fail to get the specific value of map in redis", e);
			return null;
		}
	}

	@Override
	public boolean setHashIfAbsent(final String key, final String fieldKey,
			final String value) {
		if (value == null) {
			return false;
		}
		try {
			long result = this.getJedis().hsetnx(key, fieldKey, value);
			return result == 1 ? true : false;
		} catch (Exception e) {
			logger.error("fail to set the String value to redis", e);
			return false;
		}
	}

	@Override
	public int incrByHash(final String key, final String fieldKey, final int increment) {
		try {
			return Integer.valueOf(this.getJedis().hincrBy(key, fieldKey, increment).toString());
		} catch (Exception e) {
			logger.error("fail to set the String value to redis", e);
			return 0;
		}
	}

	@Override
	public void deleteHash(final String key, final String fieldKey) {
		if (key == null) {
			return;
		}
		try {
			this.getJedis().hdel(key, fieldKey);
		} catch (Exception e) {
			logger.error("fail to delete the specific key of map in redis", e);
		}
	}

	@Override
	public void setObject(final String key, final Object value) {
		if (value == null) {
			return;
		}
		try {
			String jsonStr = JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
			this.getJedis().set(key, jsonStr);
		} catch (Exception e) {
			logger.error("fail to set the Object value to redis", e);
		}
	}

	@Override
	public Object getObject(final String key) {
		try {
			String value = this.getJedis().get(key);
			if (value != null) {
				return value;
			}
		} catch (Exception e) {
			logger.error("fail to get the Object from redis", e);			
		}
		return null;
	}

	@Override
	public void del(final String... key) {
		try {
			this.getJedis().del(key);
		} catch (Exception e) {
			logger.error("fail to get the Object from redis", e);
		}
	}

	@Override
	public void increaseValue(final String key) {
		try {
			this.getJedis().incr(key);
		} catch (Exception e) {
			logger.error("value++ is error");
		}
	}

	@Override
	public List<String> getValuesByFuzzyKey(final String fuzzyKey) {
		try {
			this.getJedis().keys(fuzzyKey);
			Set<String> keys = this.getJedis().keys(fuzzyKey);
			List<String> kList = new ArrayList<String>(keys);
			List<String> vList = new ArrayList<String>();
			for (String k : kList) {
				vList.add(this.getJedis().get(k));
			}
			return vList;
		} catch (Exception e) {
			logger.error("fail to set the Object value to redis" + e);
		}
		return null;
	}

	@Override
	public List<String> getKeysByFuzzyKey(final String fuzzyKey) {
		try {			
			Set<String> keys = this.getJedis().keys(fuzzyKey);
			List<String> list = new ArrayList<String>(keys);
			return list;
		} catch (Exception e) {
			logger.error("fail to set the Object value to redis" + e);
		}
		return null;
	}

	@Override
	public void setSetData(final String key, final String data) {
		try {
			this.getJedis().sadd(key, data);
		} catch (Exception e) {
			logger.error("fail to set the data:{} to set:{}", data, key);
		}
	}

	@Override
	public void deleteSetData(final String key, final String data) {
		try {
			this.getJedis().srem(key, data);		
		} catch (Exception e) {
			logger.error("fail to delete data:{} from set:{}", data, key);
		}
	}

	@Override
	public boolean isExistSetData(final String key, final String data) {
		try {
			return this.getJedis().sismember(key, data);
		} catch (Exception e) {
			logger.error("fail to judge whether the member exist in the set", e);
			return false;
		}
	}

}