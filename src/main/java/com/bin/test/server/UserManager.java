package com.bin.test.server;

import com.sun.javafx.tools.packager.Log;
import io.netty.channel.socket.SocketChannel;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangbin on 16/11/2.
 */
public final class UserManager {
    private static UserManager manager;
    private static Map<String, SocketChannel> userList = new ConcurrentHashMap();

    private UserManager(){
    }

    /**
     * singtom
     * @return
     */
    public static UserManager getInstance(){
        if(manager  == null){
            synchronized (UserManager.class) {
                if(manager  == null){
                    manager = new UserManager();
                }
            }
        }
        return manager;
    }

    /**
     * add user
     * @param clientID
     * @param channel
     */
    public  void addUser(String clientID, SocketChannel channel){
        if(clientID != null && clientID.length()>0)
        userList.put(clientID, channel);

    }

    /**
     * get user
     * @param clientID
     * @return
     */
    public  SocketChannel getUserChannel(String clientID){
        return (SocketChannel) userList.get(clientID);
    }

    /**
     * del user
     * @param clientID
     */
    public  void removeUser(String clientID){
        if(clientID != null && clientID.length()>0)
        userList.remove(clientID);
    }

    /**
     * del channel
     * @param channel
     */
    public  void removeChannel(SocketChannel channel){
        Iterator<Map.Entry<String, SocketChannel>> entries = userList.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, SocketChannel> entry = entries.next();
            if(entry.getValue().equals(channel)){
                entries.remove();
                Log.debug("removeChannel()... " + entry.getKey());
                return;
            }
        }
    }

    public static Map<String, SocketChannel> getUserList() {
        return userList;
    }

    public static void setUserList(Map<String, SocketChannel> userList) {
        UserManager.userList = userList;
    }

    public  int getTotalUserCount(){
        return userList.size();
    }


    public  void clearAll(){
        userList.clear();
    }
}
