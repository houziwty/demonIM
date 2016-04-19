package netty.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.Tuple;

public interface CacheClient {
	
	
	void cleanup();

	Long decr(String key);

	void del(String... keys);

	Boolean exists(byte[] key);

	Boolean exists(String key);

	Long expire(String key, int seconds);

	byte[] get(byte[] key);

	String get(String key);

	Long hdel(byte[] key, byte[] field);

	long hdel(String key, String field);

	Boolean hexists(byte[] key, byte[] field);

	byte[] hget(byte[] key, byte[] field);

	String hget(String key, String field);

	Map<byte[], byte[]> hgetAll(byte[] key);

	Map<String, String> hgetAll(String key);

	long hincrBy(String key, String field, long value);

	List<byte[]> hmget(byte[] key, byte[]... fields);

	List<String> hmget(String key, String... fields);

	String hmset(byte[] key, Map<byte[], byte[]> hash);

	String hmset(String key, Map<String, String> hash);

	Long hset(byte[] key, byte[] field, byte[] value);

	Long hset(String key, String field, String value);

	Long incr(String key);

	Set<byte[]> keys(byte[] pattern);

	Set<String> keys(String pattern);

	Long llen(String key);

	Long lpush(byte[] key, byte[] value);

	Long lpush(String key, String value);

	List<byte[]> lrange(byte[] key, int start, int end);

	List<String> lrange(String key, int start, int end);

	List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline);

	Long rpush(byte[] key, byte[] value);

	Long sadd(byte[] key, byte[] member);

	Long sadd(String key, String member);

	Long scard(byte[] key);

	Long scard(String key);

	void set(byte[] key, byte[] value);

	void set(String key, String value);

	String setex(byte[] key, int seconds, byte[] value);

	Long setnx(byte[] key, byte[] value);

	long setnx(String key, String value);

	Boolean sismember(byte[] key, byte[] member);

	Boolean sismember(String key, String member);

	Set<byte[]> smembers(byte[] key);

	Set<String> smembers(String key);

	List<byte[]> sort(String key, String patterns);

	byte[] spop(byte[] key);

	String spop(String key);

	byte[] srandmember(byte[] key);

	String srandmember(String key);

	Long srem(byte[] key, byte[] member);

	Long srem(String key, String member);

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

	long zrem(byte[] key, byte[] member);

	long zrem(String key, String member);

	Long zremrangeByScore(byte[] key, double start, double end);

	Long zremrangeByScore(String key, double start, double end);

	Long zrank(byte[] key, byte[] member);

	Long zrevrank(byte[] key, byte[] member);
	
	Long lpush(String key, Object[] objs);

	
	

}
