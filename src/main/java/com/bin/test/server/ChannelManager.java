package com.bin.test.server;

import io.netty.channel.socket.SocketChannel;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangbin on 16/11/2.
 */
public final class ChannelManager {
    private static ChannelManager manager;
    private static Map<String, SocketChannel> userList = new ConcurrentHashMap();
    private ChannelManager(){
    }

    public static ChannelManager getInstance(){
        if(manager  == null){
            synchronized (ChannelManager.class) {
                if(manager  == null){
                    manager = new ChannelManager();
                }
            }
        }
        return manager;
    }

    public void addChannel(String key,SocketChannel channel){
        userList.put(key,channel);
    }
    public SocketChannel getChannel(String key){
        return userList.get(key);
    }
    public void delChannel(String key){
        userList.remove(key);
    }
    public void removeChannel(SocketChannel channel){
        Iterator<Map.Entry<String, SocketChannel>> entries = userList.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, SocketChannel> entry = entries.next();
            if(entry.getValue().equals(channel)){
                entries.remove();
                return;
            }
        }
    }

}
