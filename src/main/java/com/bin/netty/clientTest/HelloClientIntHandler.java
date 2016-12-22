package com.bin.netty.clientTest;

import com.bin.netty.CRC16CCITT;
import com.bin.netty.Message;
import com.bin.netty.constants.JTConstant;
import io.netty.buffer.Unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {
    private static Log	logger	= LogFactory.getLog(HelloClientIntHandler.class);
    @Override
    // 读取服务端的信息
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("HelloClientIntHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        result.release();
        ctx.close();
        System.out.println("Server said:" + new String(result1));
    }
    @Override
    // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //模拟发送登陆信息

/*
        logger.info("HelloClientIntHandler.channelActive");
        String msg = "Are you ok?";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);*/




        Message msgRep = new Message(JTConstant.UP_CONNECT_REQ);
        //输出数据
        ByteBuf rspBuf = Unpooled.buffer(Message.MSG_FIX_LENGTH + 46);
        //数据体
        ByteBuf bodybuf = Unpooled.buffer(46);
        bodybuf.writeInt(0x001);
        byte[] pass = CRC16CCITT.getBytesWithLengthAfter(8, "123".getBytes());
        bodybuf.writeBytes(pass);

        byte[] ip = CRC16CCITT.getBytesWithLengthAfter(32,"192.168.1.55".getBytes());
        bodybuf.writeBytes(ip);

        bodybuf.writeShort(Short.valueOf("9000"));

        //数据头
        ByteBuf headbuf = Unpooled.buffer(22);
        headbuf.writeInt(rspBuf.capacity()-1);
        headbuf.writeInt(msgRep.getMsgSn());
        headbuf.writeShort((short) msgRep.getMsgId());
        headbuf.writeInt(msgRep.getMsgGesscenterId());
        headbuf.writeBytes(msgRep.getVersionFlag());
        headbuf.writeByte(0);
        headbuf.writeInt(10);

        //加入crccode验证码
        rspBuf.writeBytes(headbuf).writeBytes(bodybuf);
        ByteBuf finalBuf=rspBuf.copy();
        byte[] b = finalBuf.array();
        finalBuf.getBytes(0, b);
        int crcvalue= CRC16CCITT.crc16(b);
        finalBuf.writeShort(crcvalue);

        //转义
        byte[] bytes =finalBuf.copy().array();
        ByteBuf formatBuf = Unpooled.buffer(finalBuf.readableBytes());
        CRC16CCITT.formatBuffer(bytes,formatBuf);
        ByteBuf buf=Unpooled.buffer(formatBuf.readableBytes()+2);
        buf.writeByte(Message.MSG_HEAD);
        buf.writeBytes(formatBuf);
        buf.writeByte(Message.MSG_TALL);
        ctx.write(buf);
        ctx.flush();
    }
}