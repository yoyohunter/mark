package com.bin.netty;

import com.bin.netty.constants.JTConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PositionHandler extends ChannelInboundHandlerAdapter {
    private static Log	logger	= LogFactory.getLog(PositionHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("**********PositionHandler***************");

        Message message = (Message)msg;

        //TODO 判断消息的类型再具体对消息体进行介些
        if(message.getMsgId() == JTConstant.UP_EXG_MSG_REAL_LOCATION){
            //处理实时上传车辆定位信息

            //TODO 解析pojo从messagebody中

            //TODO 判断是否要有回应下级平台,回应信息是什么
            if(true){

            }else {

            }

        }else {
            //交个下一个handler处理数据
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("PositionHandler.channelReadComplete");
        ctx.flush();
    }

}