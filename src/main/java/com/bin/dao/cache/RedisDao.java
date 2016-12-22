/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package com.bin.dao.cache;

import com.bin.entity.City;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Desc Redis缓存访问类
 *
 * 使用protostuff序列化对象,protobuf是google的不过使用起来不方便还要创建proto文件生成相应的对象build
 * 性能方面略差一点,不过越来悦流行
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private RuntimeSchema<City> schema = RuntimeSchema.createFrom(City.class);

    public RedisDao(String host, int port) {
        jedisPool = new JedisPool(host, port);
    }

    /**
     * @Desc 获取缓存对象
     */
    public City getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //并没有实现内部序列化操作
                // get->byte[] ->反序列化 ->Object(Seckill)
                //采用自定义序列化
                //protostuff : pojo
                byte[] bytes = jedis.get(key.getBytes());
                //缓存重新获取
                if (null != bytes) {
                    //空对象
                    City seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //对象被反序列化
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @Desc 缓存对象
     * @Date 2016/7/9 15:28
     * @param
     * @return
     */
    public String putSeckill(City seckill){
        //set Object[Seckill] -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + "?????";
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                //timeout cache, here set one hour
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
