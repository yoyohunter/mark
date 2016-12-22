/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package com.bin.test.server;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @Desc Redis缓存访问
 *
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;


    /**
     * ce shi
     * @param args
     */
    public static void main(String[] args){

        SocketChannel channel=new SocketChannel() {
            public ServerSocketChannel parent() {
                return null;
            }

            public SocketChannelConfig config() {
                return null;
            }

            public InetSocketAddress localAddress() {
                return null;
            }

            public InetSocketAddress remoteAddress() {
                return null;
            }

            public boolean isInputShutdown() {
                return false;
            }

            public boolean isOutputShutdown() {
                return false;
            }

            public ChannelFuture shutdownOutput() {
                return null;
            }

            public ChannelFuture shutdownOutput(ChannelPromise future) {
                return null;
            }

            public ChannelId id() {
                return null;
            }

            public EventLoop eventLoop() {
                return null;
            }

            public boolean isOpen() {
                return false;
            }

            public boolean isRegistered() {
                return false;
            }

            public boolean isActive() {
                return false;
            }

            public ChannelMetadata metadata() {
                return null;
            }

            public ChannelFuture closeFuture() {
                return null;
            }

            public boolean isWritable() {
                return false;
            }

            public Unsafe unsafe() {
                return null;
            }

            public ChannelPipeline pipeline() {
                return null;
            }

            public ByteBufAllocator alloc() {
                return null;
            }

            public ChannelPromise newPromise() {
                return null;
            }

            public ChannelProgressivePromise newProgressivePromise() {
                return null;
            }

            public ChannelFuture newSucceededFuture() {
                return null;
            }

            public ChannelFuture newFailedFuture(Throwable cause) {
                return null;
            }

            public ChannelPromise voidPromise() {
                return null;
            }

            public ChannelFuture bind(SocketAddress localAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
                return null;
            }

            public ChannelFuture disconnect() {
                return null;
            }

            public ChannelFuture close() {
                return null;
            }

            public ChannelFuture deregister() {
                return null;
            }

            public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture disconnect(ChannelPromise promise) {
                return null;
            }

            public ChannelFuture close(ChannelPromise promise) {
                return null;
            }

            public ChannelFuture deregister(ChannelPromise promise) {
                return null;
            }

            public Channel read() {
                return null;
            }

            public ChannelFuture write(Object msg) {
                return null;
            }

            public ChannelFuture write(Object msg, ChannelPromise promise) {
                return null;
            }

            public Channel flush() {
                return null;
            }

            public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
                return null;
            }

            public ChannelFuture writeAndFlush(Object msg) {
                return null;
            }

            public <T> Attribute<T> attr(AttributeKey<T> key) {
                return null;
            }

            public <T> boolean hasAttr(AttributeKey<T> key) {
                return false;
            }

            public int compareTo(Channel o) {
                return 0;
            }
        };

        RedisDao dao=new RedisDao();

        RuntimeSchema<SocketChannel> schema = RuntimeSchema.createFrom(SocketChannel.class);

        Jedis jedis = dao.jedisPool.getResource();
        int timeout = 60 * 60;

        String key="aabb";
        byte[] bytes = ProtostuffIOUtil.toByteArray(channel, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        jedis.setex(key.getBytes(),timeout,bytes);

        System.out.println("------------fenge------------");

        byte[] bytes1 = jedis.get(key.getBytes());
        //缓存重新获取
        if (null != bytes) {
            //空对象
            SocketChannel channel1= schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, channel1, schema);
            //对象被反序列化
            System.out.println("取出的channel"+channel1);
        }





       // dao.putMsg("1570033", "cc");
      //  System.out.println(dao.getMsg("1570033"));
    }


    /**
     * gou zao
     */
    public RedisDao() {
        jedisPool = new JedisPool(new GenericObjectPoolConfig(),"101.200.241.34", 6379,2000,"YueXingHuLian@45435361094");
    }

    /**
     * simple del
     * @param msg
     */
    public void delMsg(String msg){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = msg;
                jedis.del(msg);

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * string get
     * @param msg
     * @return
     */
    public String getMsg(String msg){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = msg;
                String value=jedis.get(msg);

                if (null != value) {
                    //空对象

                    return value;
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
     * @Desc 获取缓存对象
     */
    public void putMsg(String key,String value ){
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(key,value);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
