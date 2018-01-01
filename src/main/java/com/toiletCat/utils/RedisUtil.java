package com.toiletCat.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

public class RedisUtil implements AutoCloseable {

    /**
     * redis pool map
     */
    private static Map<String, JedisPool> poolMap = new HashMap<>();

    private Jedis jedis;

    /**
     * 根据传入的properties文件名称获得或创建相应jedis链接池并获得链接
     * @param propertiesName properties文件名
     */
    public RedisUtil(String propertiesName) {
        JedisPool pool = initPool(propertiesName);
        jedis = pool.getResource();
    }

    public Jedis getResource() {
        return jedis;
    }

    /**
     * 初始化Jedis连接池
     * @param propertiesName properties文件名
     */
    public static JedisPool initPool(String propertiesName) {
        if (poolMap.get(propertiesName) == null) {
            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance(propertiesName);

            String host = propertiesUtil.getProperty("redis.host");

            String port = propertiesUtil.getProperty("redis.port");

            String masterName = propertiesUtil.getProperty("redis.master.name");

            String password = propertiesUtil.getProperty("redis.password");

            String dbIndex = propertiesUtil.getProperty("redis.dbIndex");

            String timeout = propertiesUtil.getProperty("redis.connection.timeout");

            String maxWaitMillis = propertiesUtil.getProperty("redis.pool.maxWaitMillis");

            String maxTotal = propertiesUtil.getProperty("redis.pool.maxTotal");

            String maxIdle = propertiesUtil.getProperty("redis.pool.maxIdle");

            String testOnBorrow = propertiesUtil.getProperty("redis.pool.testOnBorrow");


            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(maxTotal));
            config.setMaxIdle(Integer.valueOf(maxIdle));
            config.setMaxWaitMillis(Long.valueOf(maxWaitMillis));
            config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
            JedisPool jedisPool = new JedisPool(config, host, Integer.valueOf(port), Integer.valueOf(timeout), password);
            // 将jedis连接池保存至静态变量中
            poolMap.put(propertiesName, jedisPool);
            return jedisPool;
        } else {
            return poolMap.get(propertiesName);
        }
    }

    /**
     * 返还jedis链接
     * @Description 一句话描述方法用法
     * @see
     */
    @Override
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    public Long del(String... keys) {
        return jedis.del(keys);
    }

    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    public String set(int seconds, String key, String value) {
        String str = jedis.set(key, value);
        jedis.expire(key, seconds);
        return str;
    }

    public Long setnx(int seconds, String key, String value) {
        Long l = jedis.setnx(key, value);
        if (l != 0) {
            jedis.expire(key, seconds);
        }
        return l;
    }

    public Long setnx(String key, String value) {
        return jedis.setnx(key, value);
    }

    public String setex(int seconds, String key, String value) {
        return jedis.setex(key, seconds, value);
    }

    public Long setrange(String key, Long offset, String value) {
        return jedis.setrange(key, offset, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public String mset(String... keysvalues) {
        return jedis.mset(keysvalues);
    }

    public Long msetnx(String... keysvalues) {
        return jedis.msetnx(keysvalues);
    }

    public String getset(int seconds, String key, String value) {
        String str = jedis.getSet(key, value);
        jedis.expire(key, seconds);
        return str;
    }

    public String getset(String key, String value) {
        return jedis.getSet(key, value);
    }

    public String getrange(String key, Long startOffset, Long endOffset) {
        return jedis.getrange(key, startOffset, endOffset);
    }

    public List<String> mget(String... keys) {
        return jedis.mget(keys);
    }

    public Long sadd(int seconds, String key, String... members) {
        Long l = jedis.sadd(key, members);
        jedis.expire(key, seconds);
        return l;
    }

    public Long sadd(String key, String... members) {
        return jedis.sadd(key, members);
    }

    public Set<String> smembers(String key) {
        return jedis.smembers(key);
    }

    public Long srem(String key, String... members) {
        return jedis.srem(key, members);
    }

    public String spop(String key) {
        return jedis.spop(key);
    }

    public Set<String> sdiff(String... keys) {
        return jedis.sdiff(keys);
    }

    public Long sdiffstore(String dstkey, String... keys) {
        return jedis.sdiffstore(dstkey, keys);
    }

    public Long sdiffstore(String dstkey, int seconds, String... keys) {
        Long l = jedis.sdiffstore(dstkey, keys);
        jedis.expire(dstkey, seconds);
        return l;
    }

    public Set<String> sinter(String... keys) {
        return jedis.sinter(keys);
    }

    public Long sinterstore(String dstkey, String... keys) {
        return jedis.sinterstore(dstkey, keys);
    }

    public Long sinterstore(String dstkey, int seconds, String... keys) {
        Long l = jedis.sinterstore(dstkey, keys);
        jedis.expire(dstkey, seconds);
        return l;
    }

    public Set<String> sunion(String... keys) {
        return jedis.sunion(keys);
    }

    public Long sunionstore(String dstkey, String... keys) {
        return jedis.sunionstore(dstkey, keys);
    }

    public Long sunionstore(String dstkey, int seconds, String... keys) {
        Long l = jedis.sunionstore(dstkey, keys);
        jedis.expire(dstkey, seconds);
        return l;
    }

    public Long smove(String srckey, String dstkey, String member) {
        return jedis.smove(srckey, dstkey, member);
    }

    public Long scard(String key) {
        return jedis.scard(key);
    }

    public Boolean sismember(String key, String member) {
        return jedis.sismember(key, member);
    }

    public String srandmember(String key) {
        return jedis.srandmember(key);
    }

    public Long strlen(String key) {
        return jedis.strlen(key);
    }

    public Long incr(int seconds, String key) {
        Long l = jedis.incr(key);
        jedis.expire(key, seconds);
        return l;
    }

    public Long incr(String key) {
        return jedis.incr(key);
    }

    public Long incrby(int seconds, String key, Long integer) {
        Long l = jedis.incrBy(key, integer);
        jedis.expire(key, seconds);
        return l;
    }

    public Long incrby(String key, Long integer) {
        return jedis.incrBy(key, integer);
    }

    public Long decr(int seconds, String key) {
        Long l = jedis.decr(key);
        jedis.expire(key, seconds);
        return l;
    }

    public Long decr(String key) {
        return jedis.decr(key);
    }

    public Long decrby(int seconds, String key, Long integer) {
        Long l = jedis.decrBy(key, integer);
        jedis.expire(key, seconds);
        return l;
    }

    public Long decrby(String key, Long integer) {
        return jedis.decrBy(key, integer);
    }

    public Long append(String key, String value) {
        return jedis.append(key, value);
    }

    public Long lpush(int seconds, String key, String... strings) {
        Long l = jedis.lpush(key, strings);
        jedis.expire(key, seconds);
        return l;
    }

    public Long lpush(String key, String... strings) {
        return jedis.lpush(key, strings);
    }

    public Long rpush(String key, String... strings) {
        return jedis.rpush(key, strings);
    }

    public Long rpush(int seconds, String key, String... strings) {
        Long l = jedis.rpush(key, strings);
        jedis.expire(key, seconds);
        return l;
    }

    public String lset(String key, Long index, String value) {
        return jedis.lset(key, index, value);
    }

    public Long lrem(String key, Long count, String value) {
        return jedis.lrem(key, count, value);
    }

    public String ltrim(String key, Long start, Long end) {
        return jedis.ltrim(key, start, end);
    }

    public String lpop(String key) {
        return jedis.lpop(key);
    }

    public String rpop(String key) {
        return jedis.rpop(key);
    }

    public String rpoplpush(String srckey, String dstkey) {
        return jedis.rpoplpush(srckey, dstkey);
    }

    public String lindex(String key, Long index) {
        return jedis.lindex(key, index);
    }

    public Long llen(String key) {
        return jedis.llen(key);
    }

    public Long hset(int seconds, String key, String field, String value) {
        Long l = jedis.hset(key, field, value);
        jedis.expire(key, seconds);
        return l;
    }

    public Long hset(String key, String field, String value) {
        return jedis.hset(key, field, value);
    }

    public String hget(String key, String field) {
        return jedis.hget(key, field);
    }

    public Long hsetnx(String key, String field, String value) {
        return jedis.hsetnx(key, field, value);
    }

    public Long hsetnx(int seconds, String key, String field, String value) {
        Long l = jedis.hsetnx(key, field, value);
        if (l != 0) {
            jedis.expire(key, seconds);
        }
        return l;
    }

    public String hmset(int seconds, String key, Map<String, String> hash) {
        String str = jedis.hmset(key, hash);
        jedis.expire(key, seconds);
        return str;
    }

    public String hmset(String key, Map<String, String> hash) {
        return jedis.hmset(key, hash);
    }

    public List<String> hmget(String key, String... fields) {
        return jedis.hmget(key, fields);
    }

    public Long hincrby(String key, String field, Long value) {
        return jedis.hincrBy(key, field, value);
    }

    public Boolean hexists(String key, String field) {
        return jedis.hexists(key, field);
    }

    public Long hlen(String key) {
        return jedis.hlen(key);
    }

    public Long hdel(String key, String... fields) {
        return jedis.hdel(key, fields);
    }

    public Set<String> hkeys(String key) {
        return jedis.hkeys(key);
    }

    public List<String> hvals(String key) {
        return jedis.hvals(key);
    }

    public Map<String, String> hgetAll(String key) {
        return jedis.hgetAll(key);
    }

    public Long zadd(String key, Double score, String member) {
        return jedis.zadd(key, score, member);
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return jedis.zadd(key, scoreMembers);
    }

    public Long zadd(int seconds, String key, Double score, String member) {
        Long l = jedis.zadd(key, score, member);
        jedis.expire(key, seconds);
        return l;
    }

    public Long zadd(int seconds, String key, Map<String, Double> scoreMembers) {
        Long l = jedis.zadd(key, scoreMembers);
        jedis.expire(key, seconds);
        return l;
    }

    public Long zrem(String key, String... members) {
        return jedis.zrem(key, members);
    }

    public Double zincrby(String key, Double score, String member) {
        return jedis.zincrby(key, score, member);
    }

    public Long zrank(String key, String member) {
        return jedis.zrank(key, member);
    }

    public Long zrevrank(String key, String member) {
        return jedis.zrevrank(key, member);
    }

    public Set<String> zrevrange(String key, Long start, Long end) {
        return jedis.zrevrange(key, start, end);
    }

    public Set<String> zrange(String key, Long start, Long end) {
        return jedis.zrange(key, start, end);
    }

    public Set<String> zrangebyscore(String key, Double min, Double max) {
        return jedis.zrangeByScore(key, min, max);
    }

    public Set<String> zrangebyscore(String key, String min, String max) {
        return jedis.zrangeByScore(key, min, max);
    }

    public Set<String> zrangebyscore(String key, String min, String max, int offset, int count) {
        return jedis.zrangeByScore(key, min, max, offset, count);
    }

    public Set<String> zrangebyscore(String key, Double min, Double max, int offset, int count) {
        return jedis.zrangeByScore(key, min, max, offset, count);
    }

    public Long zcount(String key, Double min, Double max) {
        return jedis.zcount(key, min, max);
    }

    public Long zcount(String key, String min, String max) {
        return jedis.zcount(key, min, max);
    }

    public Long zcard(String key) {
        return jedis.zcard(key);
    }

    public Double zscore(String key, String member) {
        return jedis.zscore(key, member);
    }

    public Long zremrangeByRank(String key, Long start, Long end) {
        return jedis.zremrangeByRank(key, start, end);
    }

    public Long zremrangeByScore(String key, Double start, Double end) {
        return jedis.zremrangeByScore(key, start, end);
    }

    public Long zremrangebyscore(String key, String start, String end) {
        return jedis.zremrangeByScore(key, start, end);
    }

    public Set<String> keys(String pattern) {
        return jedis.keys(pattern);
    }

    public List<String> lrange(String key, Long start, Long end) {
        return jedis.lrange(key, start, end);
    }

    public String flushDB() {
        return jedis.flushDB();
    }

    public String flushAll() {
        return jedis.flushAll();
    }

    public Boolean exists(String key) {
        return jedis.exists(key);
    }

    public Long expire(String key, int seconds) {
        return jedis.expire(key, seconds);
    }

    public String info() {
        return jedis.info();
    }

    public String select(int dbIndex) {
        return jedis.select(dbIndex);
    }

    public long ttl(String key) {
        return jedis.ttl(key);
    }

}
