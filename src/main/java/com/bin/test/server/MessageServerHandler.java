package com.bin.test.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by zhangbin on 16/11/2.
 */
public class MessageServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelManager.getInstance().removeChannel((SocketChannel)ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String aa= ctx.channel().remoteAddress().toString();
        String ip = aa.substring(1,aa.length()-1).split(":")[0];

        System.out.printf("----------------ip-----"+ip);

        ChannelManager.getInstance().addChannel(ip,(SocketChannel)ctx.channel());
        String ss=(String)msg;
        String[] arr=ss.split(":");

        SocketChannel channel=ChannelManager.getInstance().getChannel(arr[0].trim());
        channel.writeAndFlush(arr[1].trim());

    }
}