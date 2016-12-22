package com.bin.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class OutboundHandler2 extends ChannelOutboundHandlerAdapter {
    private static Log	logger	= LogFactory.getLog(OutboundHandler2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("**********OutboundHandler2***************");

        logger.info("OutboundHandler2.write");

        String response = "I am zhangbin!";


        super.write(ctx, msg, promise);
        System.out.println("是否还会执行上一行代码**********"+response.getBytes().length);
    }
}