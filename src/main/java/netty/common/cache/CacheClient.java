package netty.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.Tuple;

public interface CacheClient {
	
	
	public void cleanUp();
	
	public Long decr(String key);
	
	public void del(String... keys);
	
	public Boolean exists(byte[] key);
	
	public Boolean exists(String key);
	
	public Long expire(String key, int seconds);

	public byte[] get(byte[] key);

	public String get(String key);

	public Long hdel(byte[] key, byte[] field);

	public long hdel(String key, String field);

	public Boolean hexists(byte[] key, byte[] field);

	public byte[] hget(byte[] key, byte[] field);

	public String hget(String key, String field);
	
	public Long incr(String key);

	public Set<byte[]> keys(byte[] pattern);

	public Set<String> keys(String pattern);

	public Long llen(String key);

	public Long lpush(byte[] key, byte[] value);

	public Long lpush(String key, String value);

	public List<byte[]> lrange(byte[] key, int start, int end);

	public List<String> lrange(String key, int start, int end);

	List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline);

	public Long rpush(byte[] key, byte[] value);

	public Long sadd(byte[] key, byte[] member);

	public Long sadd(String key, String member);

	public Long scard(byte[] key);

	public Long scard(String key);

	public void set(byte[] key, byte[] value);

	public void set(String key, String value);

	public String setex(byte[] key, int seconds, byte[] value);

	public Long setnx(byte[] key, byte[] value);

	public long setnx(String key, String value);

	public Boolean sismember(byte[] key, byte[] member);

	public Boolean sismember(String key, String member);

	public Set<byte[]> smembers(byte[] key);

	public Set<String> smembers(String key);

	public List<byte[]> sort(String key, String patterns);

	public byte[] spop(byte[] key);

	public String spop(String key);

	public byte[] srandmember(byte[] key);

	public String srandmember(String key);

	public Long srem(byte[] key, byte[] member);

	public Long srem(String key, String member);

	void zadd(String key, double score, String member);

	Long zcard(byte[] key);

	Long zcard(String key);

	Long zcount(byte[] key, long min, long max);

	Long zcount(String key, long min, long max);

	Set<byte[]> zrange(byte[] key, int start, int end);

	Set<String> zrange(String key, int start, int end);
	

	Set<byte[]> zrangeByScore(byte[] key, long min, long max);

	Set<String> zrangeByScore(String key, long min, long max);

	Set<Tuple> zrangeWithScores(byte[] key, int start, int end);

	Set<Tuple> zrangeWithScores(String key, int start, int end);

	Set<Tuple> zrevRangeWithScores(String key, int start, int end);

	public long zrem(byte[] key, byte[] member);

	public long zrem(String key, String member);

	Long zremrangeByScore(byte[] key, double start, double end);

	Long zremrangeByScore(String key, double start, double end);
	
	public Long zrank(byte[] key, byte[] member);
	
	public Long zrevrank(byte[] key, byte[] member);
	
	

}
