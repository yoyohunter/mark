package com.bin.redistemp;

import java.util.Map;
import java.util.Set;


public class RedisDao {
	/**
	 * 设置新的string类型数据 key为key，value为string类型 缓存刷新时间是秒 FunctionName:setNewString.
	 *
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		RedisUtils.setString(key, value);
	}

	/**
	 * 设置原有的数据值，更新 缓存刷新时间是秒 FunctionName:setOldString.
	 *
	 * @param key
	 * @param value
	 * @param time
	 */
	/*public void setOldString(String key, String value, long time) {
		RedisUtils.setOldString(key, value, time);
	}*/

	/**
	 * 获取String值 FunctionName:getString.
	 *
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return RedisUtils.getString(key);
	}

	/**
	 * 设置key-hashMap类型，调用会覆盖之前的 缓存刷新时间是秒 FunctionName:setHM.
	 *
	 * @param key
	 * @param map
	 */
	public void setHM(String key, Map<String, String> map) {
		RedisUtils.setHM(key, map);
	}
	
	/**
	 * 通过key批量删除对应数据
	 *
	 * @author tangxiangfei
	 * @since JDK 1.7
	 */
	public void del(String keys[]) {
		RedisUtils.del(keys);
	}
	/**
	 * 通过key单条删除对应数据
	 *
	 * @since JDK 1.7
	 */
	public void del(String key) {
		RedisUtils.del(key);
	}

	/**
	 * 新增单条的key-hashMap 缓存刷新时间是秒 FunctionName:setHMSingle.
	 *
	 * @param firstKey
	 * @param secondKey
	 * @param value
	 */
	public void setHMSingle(String firstKey, String secondKey, String value) {
		RedisUtils.setHMSingle(firstKey, secondKey, value);
	}

	/**
	 * 获取key对应的整个hashmap FunctionName:getHash.
	 *
	 * @param key
	 * @return
	 */
	public Map<String, String> getHash(String key) {
		return RedisUtils.getHash(key);
	}

	/**
	 * 获取key-hashMap对应的单个值 FunctionName:getHMSingle.
	 *
	 * @param firstKey
	 * @param secondKey
	 * @return
	 */
	public String getHMSingle(String firstKey, String secondKey) {
		return RedisUtils.getHMSingle(firstKey, secondKey);
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> sortMapByKey(Map<String, String> map) {
		return RedisUtils.sortMapByKey(map);
	}
	
	/**
	 * 通过first key 和 second key 更新value值
	 * 
	 */
	public void hincrBy(String firstkey,String secondkey,Long value){
		RedisUtils.hincrBy(firstkey, secondkey, (null==value)?0:value);
	}
	
	/**
	 * 通过key 获取set
	 * @return 
	 * 
	 */
	public  Set<String> sMembers(String key){
		return  RedisUtils.sMembers(key);
	}
	
	/**
	 * 通过key 增加set记录
	 * @return 
	 * 
	 */
	public  void addSet(String key,String value){
		RedisUtils.AddSet(key,value);
	}
	
	public boolean hasSet(String key1,String key2){
		return RedisUtils.hasSetKey(key1, key2);
	}
	
	/**
	 * 删除名称为key的set中的元素member
	 */
	public void srem(String key,String member){
		RedisUtils.srem(key, member);
	}
}
