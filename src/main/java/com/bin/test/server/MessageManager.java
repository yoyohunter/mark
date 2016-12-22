package com.bin.test.server;

import com.sun.javafx.tools.packager.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangbin on 16/11/2.
 */
public class MessageManager {

    private RedisDao constantDao=new RedisDao();
    private static final String MESSAGE="It greatly simplifies and streamlines network programming such as TCP and UDP socket server.";

/*
    private static MessageManager manager;
    private LinkedBlockingQueue<String> mMessageQueue = new LinkedBlockingQueue<String>();
    private ThreadPoolExecutor mPoolExecutor = new ThreadPoolExecutor(5, 10, 15, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

    private MessageManager(){
    }

    public static MessageManager getInstance(){
        if(manager  == null){
            synchronized (MessageManager.class) {
                if(manager  == null){
                    manager = new MessageManager();
                }
            }
        }
        return manager;
    }

    public void putMessage(String message){
        try {
            mMessageQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        Map map =UserManager.getInstance().getUserList();
        while(true){
            if(!map.isEmpty()){
                try {
                    //mMessageQueue.put("1570033");
                    //String message = mMessageQueue.take();
                    Set<String> set=map.keySet();
                    for(String message:set){
                        mPoolExecutor.execute(new SendMessageTask(message));
                    }
                    Thread.sleep(5000);
                } catch (EmptyStackException e) {
                    e.printStackTrace();
                    break;
                }catch (RejectedExecutionException e){
                    Log.debug("MessageManager-> 服务器消息队列已满...延时2妙后继续发送...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    continue;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }

    public void stop(){
        Log.debug("MessageManager-> stop()... ");
        mMessageQueue.clear();
        mPoolExecutor.shutdownNow();
    }

    class SendMessageTask implements Runnable{
        private String message;

        public SendMessageTask(String message){
            this.message = message;
        }
        public void run() {

            if(message.length() > 0){
                //从缓存中查询是否又更新信息
                String value=constantDao.getMsg(message);
                if(value != null && value.trim().length()>0){
                    //产看channel情况
                    SocketChannel channel = UserManager.getInstance().getUserChannel(message);
                    if(channel != null && channel.isActive()) {
                        //处理更新信息
                        sendMsg(channel,value);
                    }else {
                        UserManager.getInstance().removeUser(message);
                    }
                }
            }
        }
    }

    private void sendMsg(SocketChannel channel,String value){

        String[] arr= value.split(",");
        for(String ss : arr){
            switch(ss){
                case "aa":
                    //TODO 处理业务,推送数据
                    //第一个业务处理 DriverQueryTaskHandler
                    System.out.println("------------这里是aa-------------");
                    ByteBuf echo= Unpooled.copiedBuffer("AAAAAaaaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaAAAA" .getBytes());
                    channel.writeAndFlush(echo);


                    break;
                case "bb":
                    //TODO 处理业务,推送数据
                    System.out.println("------------这里是bb-------------");

                    ByteBuf sss= Unpooled.copiedBuffer("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB".getBytes());
                    channel.writeAndFlush(sss);

                    break;
                case "cc":
                    //TODO 处理业务,推送数据

                    System.out.println("------------这里是cc-------------");
                    ByteBuf cc= Unpooled.copiedBuffer("cccccccccccccccccccccccccccccc".getBytes());
                    channel.writeAndFlush(cc);
                    break;

                default:
                    System.out.println("出现还没有的推送业务,需要处理---------");
                    break;
            }
        }

    }*/
}
