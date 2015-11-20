package netty.common.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import netty.common.cache.CacheClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.util.Hashing;

public class RedisClient implements CacheClient {

	private Jedis jedis;// 非切片额客户端连接
	private static JedisPool jedisPool;// 非切片连接池
	private ShardedJedis shardedJedis;
	private static ShardedJedisPool shardedJedisPool;

	private static JedisPoolConfig config;

	// 默认超时时间
	private static final int DEFAULT_TIMEOUT = 4000;

	// 默认最大活动连接数
	private static final int MAX_ACTIVE = 20;

	// 默认最大空闲连接数
	private static final int MAX_IDLE = 10;

	// 默认最大等待
	private static final int MAX_WAIT = 100;

	public RedisClient(String servers, String app) {
		String[] hosts = servers.trim().split("\\|");
		initalShardedPool(hosts);

	}

	static {
		config = new JedisPoolConfig();
		// 最大空闲连接数, 默认8个
		config.setMaxIdle(8);
		// 最大连接数, 默认8个
		config.setMaxTotal(8);
		config.setTestOnBorrow(false);
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(1800000);

	}

	/**
	 * 初始化非切片池
	 */
	private static void initialPool() {
		jedisPool = new JedisPool(config, "127.0.0.1", 6379);
	}

	private static void initalShardedPool(String[] hosts) {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String host : hosts) {
			String[] ss = host.split(":");
			shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));
		}

		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	@Override
	public void cleanUp() {
		shardedJedisPool.destroy();
	}

	@Override
	public Long decr(String key) {
		ShardedJedis redis = shardedJedisPool.getResource();
		Long result = -1L;
		try {
			result = redis.decr(key);
		} catch (Exception ex) {
		} finally {
			shardedJedisPool.close();
		}
		return result;
	}

	@Override
	public void del(String... keys) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean exists(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hdel(String key, String field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> keys(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long llen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpush(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long scard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long scard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long setnx(String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean sismember(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> sort(String key, String patterns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] spop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String spop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] srandmember(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String srandmember(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void zadd(String key, double score, String member) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long zcard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(byte[] key, long min, long max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(String key, long min, long max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrange(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, long min, long max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, long min, long max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevRangeWithScores(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long zrem(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long zrem(String key, String member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

}
