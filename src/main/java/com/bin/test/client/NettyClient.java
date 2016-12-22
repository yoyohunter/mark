package com.bin.test.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by zhangbin on 16/11/2.
 */
public class NettyClient {
    private String TAG = "ChatClient";
    private static NettyClient client;
    private EventLoopGroup eventLoopGroup;
    private SocketChannel socketChannel;
    private String clientID;
    private String groupID;
    private final int HEART_PING_TIME = 5;


    private NettyClient(){
    }

    public static NettyClient getInstance(){
        if(client  == null){
            synchronized (NettyClient.class) {
                if(client  == null){
                    client = new NettyClient();
                }
            }
        }
        return client;
    }


    public void connect(String serverIP, int port) {
        eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(serverIP, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    socketChannel.pipeline().addLast(new StringDecoder());
                    socketChannel.pipeline().addLast(new StringEncoder());
                    socketChannel.pipeline().addLast(new NettyClientHandler());
                    /*
                    socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));//设置定长解码器
                    socketChannel.pipeline().addLast(new StringDecoder());//设置字符串解码器
                    socketChannel.pipeline().addLast(new NettyClientHandler());*/
                }
            });
            ChannelFuture future =bootstrap.connect(serverIP, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("connect server  success...");
            }
            future.channel().closeFuture().sync();
        }catch(InterruptedException e) {
            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        NettyClient c =NettyClient.getInstance();
        c.connect("127.0.0.1",9000);
    }

   /* public synchronized void sendMessage(MessageProto.Message message){
        Log.d(TAG, "sendMessage()..." + message.getBody());
        socketChannel.writeAndFlush(message);
    }


    public void sync() throws InterruptedException{
        socketChannel.closeFuture().sync();
    }


    public synchronized void stop(){
        socketChannel.writeAndFlush(createMessage(MessageType.LOGOUT, null));
        eventLoopGroup.shutdownGracefully();
    }


    public void setClientID(String clientID){
        this.clientID = clientID;
    }

    public String getClientID(){
        return this.clientID;
    }


    public void setGroupID(String groupID){
        this.groupID = groupID;
    }


    public String getGroupID(){
        return this.groupID;
    }


    public Message createMessage(MessageType type, String body){
        MessageProto.Message.Builder builder = MessageProto.Message.newBuilder();
        builder.setClientID(clientID);
        builder.setMsgType(type);
        builder.setGroupID(groupID);
        if(body != null){
            builder.setBody(body);
        }
        return builder.build();
    }*/
}