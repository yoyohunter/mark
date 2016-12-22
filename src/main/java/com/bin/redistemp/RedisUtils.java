package com.bin.redistemp;

import java.util.*;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtils {

	// Redis服务器IP
	// private static String ADDR = "10.0.56.102";

	// Redis的端口号
	// private static int PORT = 6379;

	// 访问密码
	// private static String AUTH = "";

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_TOTAL = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	private static Set<HostAndPort> hs = new HashSet<HostAndPort>();

	private static String redisModule = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			Properties pros=null;
			pros=PropertiesLoaderUtils.loadAllProperties("");
			String hostAndPorts=pros.getProperty("");
			pros.setProperty("","");


			// 0为单点模式 1为集群模式
//			redisModule = SystemProperties.get("RedisModule","1");
			//redisModule = SystemProperties.get("RedisModule");
			// 参数格式为 host:port;host:port
			// 如果为单点模式 则为host:port
			// String hostAndPorts =
//			String hostAndPorts =   SystemProperties.get("HostAndPort","172.16.130.29:10001;172.16.130.29:10002;172.16.130.29:10003");
			//String hostAndPorts = SystemProperties.get("HostAndPort");
			String[] split = hostAndPorts.split(";");
			if ("0".equals(redisModule)) {
				String string = hostAndPorts;
				String[] split2 = string.split(":");
				String ADDR = split2[0];
				Integer PORT = Integer.parseInt(split2[1]);
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_TOTAL);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);
				jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, null);
			} else if ("1".equals(redisModule)) {
				for (int i = 0; i < split.length; i++) {
					String string = split[i];
					String[] split2 = string.split(":");
					HostAndPort h = new HostAndPort(split2[0], Integer.parseInt(split2[1]));
					hs.add(h);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取JedisCluster实例
	 * 
	 * @return
	 */
	public static JedisCluster getJedisCluster() {
		try {
			// if (jedisPool != null) {
			JedisCluster jc = new JedisCluster(hs);
			return jc;
			// } else {
			// return null;
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	// public static void returnResource(final Jedis jedis) {
	// jedis.close();
	// }

	/**
	 * 释放JedisCluster资源
	 * 
	 */
	public static void returnResource(final JedisCluster jedis) {
		try {
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放JedisCluster资源
	 * 
	 */
	public static void returnResource(final Jedis jedis) {
		try {
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置新的string类型数据 key为key，value为string类型 缓存刷新时间是秒 FunctionName:setNewString.
	 *
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				if (jedis.get(key) != null) {
					jedis.del(key);
				}
				jedis.setnx(key, value);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				if (jedis.get(key) != null) {
					jedis.del(key);
				}
				jedis.setnx(key, value);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				returnResource(jedis);
			}
		}

	}

	/**
	 * 设置原有的数据值，更新 缓存刷新时间是秒 FunctionName:setOldString.
	 *
	 * @author wang
	 * @since JDK 1.7
	 * @param key
	 * @param value
	 * @param time
	 */
	/*
	 * public static void setOldString(String key, String value, long time) {
	 * JedisCluster jedis = getJedisCluster(); try{ jedis.set(key, value, "XX",
	 * "EX", time); }finally { returnResource(jedis); } }
	 */

	/**
	 * 获取String值 FunctionName:getString.
	 *
	 * @author wang
	 * @since JDK 1.7
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		String result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.get(key);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.get(key);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}

	/**
	 * 设置key-hashMap类型，调用会覆盖之前的 缓存刷新时间是秒 FunctionName:setHM.
	 *
	 * @param key
	 * @param map
	 */
	public static void setHM(String key, Map<String, String> map) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			jedis.hmset(key, map);
			returnResource(jedis);
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			jedis.hmset(key, map);
			returnResource(jedis);
		}

	}

	/**
	 * 通过key批量删除对应数据
	 *
	 */
	public static void del(String keys[]) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			jedis.del(keys);
			returnResource(jedis);
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			jedis.del(keys);
			returnResource(jedis);
		}

	}

	/**
	 * 通过key单条删除对应数据
	 */
	public static void del(String key) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			jedis.del(key);
			returnResource(jedis);
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			jedis.del(key);
			returnResource(jedis);
		}

	}

	/**
	 * 删除名称为key的set中的元素member
	 */
	public static void srem(String key, String member) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				jedis.srem(key, member);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				jedis.srem(key, member);
			} finally {
				returnResource(jedis);
			}
		}
	}

	/**
	 * 通过first key and second key 删除
	 * 
	 * @param keys
	 */
	public static void delH(String firstkey, String keys[]) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			jedis.hdel(firstkey, keys);
			returnResource(jedis);
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			jedis.hdel(firstkey, keys);
			returnResource(jedis);
		}

	}

	/**
	 * 更新second key value
	 */
	public static void hincrBy(String firstkey, String secondkey, Long value) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			jedis.hincrBy(firstkey, secondkey, (null == value) ? 0 : value);
			returnResource(jedis);
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			jedis.hincrBy(firstkey, secondkey, (null == value) ? 0 : value);
			returnResource(jedis);
		}

	}

	/**
	 * 新增单条的key-hashMap 缓存刷新时间是秒 FunctionName:setHMSingle.
	 *
	 */
	public static void setHMSingle(String firstKey, String secondKey, String value) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			jedis.hset(firstKey, secondKey, value);
			returnResource(jedis);
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			jedis.hset(firstKey, secondKey, value);
			returnResource(jedis);
		}

	}

	/**
	 * 获取key对应的整个hashmap FunctionName:getHash.
	 *
	 */
	public static Map<String, String> getHash(String key) {
		Map<String, String> result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.hgetAll(key);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.hgetAll(key);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}

	/**
	 * 获取key-hashMap对应的单个值 FunctionName:getHMSingle.
	 */
	public static String getHMSingle(String firstKey, String secondKey) {
		String result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.hget(firstKey, secondKey);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.hget(firstKey, secondKey);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}

	public static Set<String> sMembers(String key) {
		Set<String> result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.smembers(key);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.smembers(key);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}

	public static void main(String[] args) {

		Map<String, String> map = new TreeMap<String, String>();

		map.put("20160425131945", "nba");
		map.put("20160425152400", "kfc");
		map.put("20160425105650", "cba");
		map.put("20160425151750", "wnba");

		Map<String, String> resultMap = sortMapByKey(map); // 按Key进行排序

		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		System.out.println(getHash("1"));
		// JedisCluster jedisCluster = getJedisCluster();
		// jedisCluster.set("hehe", "hahah");
		// String string = jedisCluster.get("hehe");
		// jedisCluster.del("hehe");
		// String string1 = jedisCluster.get("hehe");
		// System.out.println(string1+"===========");
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String str1, String str2) {
				return str2.compareTo(str1);
			}
		});

		sortMap.putAll(map);

		return sortMap;
	}

	public static Set<String> getSet(String key) {
		Set<String> result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.smembers(key);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.smembers(key);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}
	/**获取 set的 size
	 * @param key
	 * @return
	 */
	public static long getSetSize(String key) {
		Long result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.scard(key);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.scard(key);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}

	/**
	 * 返回set中一个 ，并删除
	 * 
	 * @param key
	 * @return
	 */
	public static String getSetRandomAndRemove(String key) {
		String result = null;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.spop(key);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.spop(key);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}

	/**
	 * set 添加元素
	 * 
	 * @param key
	 * @return
	 */
	public static void AddSet(String key, String value) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				jedis.sadd(key, value);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				jedis.sadd(key, value);
			} finally {
				returnResource(jedis);
			}
		}

	}

	/**
	 * set 删除元素
	 * 
	 * @param key
	 * @return
	 */
	public static void delSet(String key, String value) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				jedis.srem(key, value);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				jedis.srem(key, value);
			} finally {
				returnResource(jedis);
			}
		}

	}

	/**
	 * 定时清空数据
	 * 
	 * @param key
	 * @return
	 */
	public static void expire(String key, int time) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				jedis.expire(key, time);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				jedis.expire(key, time);
			} finally {
				returnResource(jedis);
			}
		}

	}

	public static void updateSet(String key, Set<String> set) {
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				jedis.del(key);
				for (String str : set) {
					jedis.sadd(key, str);
				}
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				jedis.del(key);
				for (String str : set) {
					jedis.sadd(key, str);
				}
			} finally {
				returnResource(jedis);
			}
		}
	}

	public static void setHMString(String firstKey, String secondKey, String value) {
		setHMSingle(firstKey, secondKey, value);
	}

	/*
	 * public static void updateString(String key, String value) { try {
	 * jedis.hdel(key, fields) setOldString(key, value, 100000); } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */
	public static boolean hasSetKey(String key1, String key2) {
		boolean result = false;
		if ("0".equals(redisModule)) {
			Jedis jedis = getJedis();
			try {
				result = jedis.sismember(key1, key2);
			} finally {
				returnResource(jedis);
			}
		} else if ("1".equals(redisModule)) {
			JedisCluster jedis = getJedisCluster();
			try {
				result = jedis.sismember(key1, key2);
			} finally {
				returnResource(jedis);
			}
		}
		return result;
	}
/*

	public static Set<String> getSleepDaySet() {
		Set<String> set = getSet(RedisConstans.SLEEP_DAY_KEY);
		if (set == null || set.size() == 0) {
			TradeDayUtil.createCheckType();
			TradeDayUtil.createSleepDay();
			TradeDayUtil.createEnableTime();
			set = getSet(RedisConstans.SLEEP_DAY_KEY);
		}
		return set;
	}
*/

	/**
	 * 根据key值删除
	 * 
	 * @param key1
	 * @param fields
	 * @return
	 */
	public static Long hdel(String key1, String fields) {
		JedisCluster jedisCluster = getJedisCluster();
		Long result = 0L;
		try {
			result = jedisCluster.hdel(key1, fields);
		} finally {
			returnResource(jedisCluster);
		}
		return result;
	}
}