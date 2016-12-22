package com.bin.netty.handler;

import com.bin.netty.LoginHandler;
import com.bin.netty.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by zhangbin on 16/10/20.
 */
public class DecodeHandler extends ChannelInboundHandlerAdapter {
    private static Log logger = LogFactory.getLog(LoginHandler.class);



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf=(ByteBuf)msg;
        int head = byteBuf.getByte(0);
        int tail = byteBuf.getByte(byteBuf.writerIndex() - 1);
        //对信息头和尾部进行判断
        if(!(head == Message.MSG_HEAD && tail == Message.MSG_TALL)){
            return;
        }
        byteBuf.skipBytes(1);
        Message message = buildMessage(byteBuf);

        //向下面的handler传递数据
        ctx.fireChannelRead(message);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("LoginHandler.channelReadComplete");
        ctx.flush();
    }



    private Message buildMessage(ByteBuf buffer){
        Message msg = new Message();
        msg.setMsgLength(buffer.readUnsignedInt());
        msg.setMsgSn(buffer.readInt());//4byte
        msg.setMsgId(buffer.readUnsignedShort());//2byte
        msg.setMsgGesscenterId(buffer.readUnsignedInt());//4byte
        msg.setVersionFlag(buffer.readBytes(3).array());//3byte
        msg.setEncryptFlag(buffer.readUnsignedByte());//1byte
        msg.setEncryptKey(buffer.readUnsignedInt());//4byte
/*
        if(buffer.readableBytes() >= 9){
            buffer.skipBytes(buffer.readableBytes() - 8);
        }
        */
        ByteBuf bodyBytes =buffer.readBytes(buffer.readableBytes() -3);
        msg.setMsgBody(bodyBytes);
        msg.setCrcCode(buffer.readUnsignedShort());//2byte
        buffer.skipBytes(1);
        buffer.clear();
        return msg;
    }
}
