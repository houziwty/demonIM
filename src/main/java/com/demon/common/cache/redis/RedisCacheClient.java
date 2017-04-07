package com.demon.common.cache.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demon.common.cache.CacheClient;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class RedisCacheClient implements CacheClient {

	 Logger logger = Logger.getLogger(RedisCacheClient.class);

	// 默认超时时间
	private static final int DEFAULT_TIMEOUT = 400;

	// 默认最大活动连接数
	private static final int MAX_ACTIVE = 20;

	// 默认最大空闲连接数
	private static final int MAX_IDEL = 10;

	// 默认最大等待
	private static final int MAX_WAIT = 100;

	private static ShardedJedisPool pool;

	// private static JedisPool pool;
	private static JedisPoolConfig config;

	static {
		config = new JedisPoolConfig();
		// 最大空闲连接数 默认8个
		config.setMaxIdle(MAX_IDEL);
		// 最大连接数, 默认8个
		config.setMaxTotal(MAX_ACTIVE);

		config.setMaxWaitMillis(MAX_WAIT);
		//
		config.setTestWhileIdle(true);
		//
		config.setTestOnBorrow(false);

		// timeBetweenEvictionRunsMillis
		config.setTimeBetweenEvictionRunsMillis(DEFAULT_TIMEOUT);
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(1800000);

	}

	public RedisCacheClient() {

	}

	public RedisCacheClient(String servers, String app) {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		try {
			String[] hosts = servers.trim().split("\\|");
			for (String host : hosts) {
				String[] address = host.split(":");
				JedisShardInfo shard = new JedisShardInfo(address[0],
						Integer.parseInt(address[1]));
				shards.add(shard);
			}
			pool = new ShardedJedisPool(config, shards, Hashing.MURMUR_HASH,
					Sharded.DEFAULT_KEY_TAG_PATTERN);
		} catch (Exception ex) {
			// logger.error("App="+app+"<Servers="+servers+"<shards.size="+shards.size()+"<");
		}

	}

	private void close(ShardedJedis redis) {
		if (redis != null)
			redis.close();
	}

	@Override
	public void cleanup() {
		pool.destroy();
	}

	@Override
	public Long decr(String key) {
		ShardedJedis redis = pool.getResource();
		Long result = -1L;
		try {
			result = redis.decr(key);
		} catch (Exception ex) {

		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public void del(String... keys) {
		ShardedJedis redis = pool.getResource();
		try {
			for (String key : keys) {
				redis.del(key);
			}
		} catch (Exception e) {
		} finally {
			this.close(redis);
		}
	}

	@Override
	public Boolean exists(byte[] key) {
		ShardedJedis redis = pool.getResource();
		Boolean result = false;
		try {
			result = redis.exists(key);
		} catch (Exception e) {

		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public Boolean exists(String key) {
		ShardedJedis redis = pool.getResource();
		Boolean result = false;
		try {
			result = redis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		ShardedJedis redis = pool.getResource();
		Long result = -1L;
		try {

			result = redis.expire(key, seconds);
		} catch (Exception ex) {
		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public byte[] get(byte[] key) {
		ShardedJedis redis = pool.getResource();
		byte[] result = null;
		try {
			result = redis.get(key);
		} catch (Exception ex) {
		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public String get(String key) {
		ShardedJedis redis = pool.getResource();
		String result = null;
		try {
			result = redis.get(key);
		} catch (Exception ex) {
		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public Long hdel(byte[] key, byte[] field) {
		return null;
	}

	@Override
	public long hdel(String key, String field) {
		return 0;
	}

	public Boolean hexists(String key, String field) {
		ShardedJedis redis = pool.getResource();
		boolean result = false;
		try {
			result = redis.hexists(key, field);
		} catch (Exception ex) {
			 logger.error("hexists:", ex);
		} finally {
			this.close(redis);
		}

		return result;
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {
		ShardedJedis redis = pool.getResource();
		boolean result = false;
		try {
			result = redis.hexists(key, field);
		} catch (Exception ex) {
			ex.printStackTrace();
			// logger.error("hexists:", ex);
		} finally {
			this.close(redis);
		}

		return result;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		return null;
	}

	@Override
	public String hget(String key, String field) {
		return null;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		return null;
	}

	// 获取全部哈希值
	@Override
	public Map<String, String> hgetAll(String key) {
		ShardedJedis redis = pool.getResource();
		Map<String, String> result = new HashMap<String, String>();
		try {
			result = redis.hgetAll(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public long hincrBy(String key, String field, long value) {
		return 0;
	}

	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		return null;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return null;
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		return null;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return null;
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		return null;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Long result = -1L;
		ShardedJedis redis = pool.getResource();
		try {
			result = redis.hset(key, field, value);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public Long incr(String key) {
		return null;
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		return null;
	}

	@Override
	public Set<String> keys(String pattern) {
		return null;
	}

	@Override
	public Long llen(String key) {
		return null;
	}

	@Override
	public Long lpush(byte[] key, byte[] value) {
		return null;
	}

	@Override
	public Long lpush(String key, String value) {
		ShardedJedis redis = pool.getResource();
		Long resutl = -1L;
		try {
			redis.lpush(key, value);
		} catch (Exception ex) {
			 logger.error("lpush", ex);
		} finally {
			this.close(redis);
		}
		return resutl;
	}
	
	@Override
	public Long lpush(String key, Object[] objs) {
		ShardedJedis redis = pool.getResource();
		ShardedJedisPipeline pipeline = redis.pipelined();
		Long resutl = -1L;
		try {
			for (Object obj : objs)
				redis.lpush(key, obj.toString());
		} catch (Exception ex) {
			 logger.error("lpush", ex);
		} finally {
			pipeline.sync();
			this.close(redis);
		}
		return resutl;
	}

	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		ShardedJedis redis = pool.getResource();
		List<byte[]> result = new ArrayList<byte[]>();
		try {
			result = redis.lrange(key, start, end);
		} catch (Exception e) {

		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		ShardedJedis redis = pool.getResource();
		List<String> result = new ArrayList<String>();
		try {
			result = redis.lrange(key, start, end);
		} catch (Exception e) {

		} finally {
			this.close(redis);
		}
		return result;
	}

	@Override
	public List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline) {
		return null;
	}

	@Override
	public Long rpush(byte[] key, byte[] value) {
		return null;
	}

	@Override
	public Long sadd(byte[] key, byte[] member) {
		return null;
	}

	@Override
	public Long sadd(String key, String member) {
		return null;
	}

	@Override
	public Long scard(byte[] key) {
		return null;
	}

	@Override
	public Long scard(String key) {
		return null;
	}

	@Override
	public void set(byte[] key, byte[] value) {

	}

	@Override
	public void set(String key, String value) {

		ShardedJedis redis = pool.getResource();

		try {
			if (redis != null) {
				redis.set(key, value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.close(redis);
		}
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		return null;
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {
		return null;
	}

	@Override
	public long setnx(String key, String value) {
		return 0;
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {
		return null;
	}

	@Override
	public Boolean sismember(String key, String member) {
		return null;
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		return null;
	}

	@Override
	public List<byte[]> sort(String key, String patterns) {
		return null;
	}

	@Override
	public byte[] spop(byte[] key) {
		return null;
	}

	@Override
	public String spop(String key) {
		return null;
	}

	@Override
	public byte[] srandmember(byte[] key) {
		return null;
	}

	@Override
	public String srandmember(String key) {
		return null;
	}

	@Override
	public Long srem(byte[] key, byte[] member) {
		return null;
	}

	@Override
	public Long srem(String key, String member) {
		return null;
	}

	@Override
	public void zadd(String key, double score, String member) {

	}

	@Override
	public Long zcard(byte[] key) {
		return null;
	}

	@Override
	public Long zcard(String key) {
		return null;
	}

	@Override
	public Long zcount(byte[] key, long min, long max) {
		return null;
	}

	@Override
	public Long zcount(String key, long min, long max) {
		return null;
	}

	@Override
	public Set<byte[]> zrange(byte[] key, int start, int end) {
		return null;
	}

	@Override
	public Set<String> zrange(String key, int start, int end) {
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, long min, long max) {
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, long min, long max) {
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		return null;
	}

	@Override
	public Set<Tuple> zrevRangeWithScores(String key, int start, int end) {
		return null;
	}

	@Override
	public long zrem(byte[] key, byte[] member) {
		return 0;
	}

	@Override
	public long zrem(String key, String member) {
		return 0;
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {
		return null;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		return null;
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {
		return null;
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {
		return null;
	}



}
