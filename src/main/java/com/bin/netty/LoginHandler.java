package com.bin.netty;

import com.bin.netty.constants.JTConstant;
import com.bin.netty.messagebody.up.LoginReq;
import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginHandler extends ChannelInboundHandlerAdapter {
    private static Log logger = LogFactory.getLog(LoginHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("LoginHandler.channelRead: ctx :" + ctx);

        System.out.println("**********LoginHandler***************");

        Message message = (Message)msg;

        if(message.getMsgId() == JTConstant.UP_CONNECT_REQ){
            //处理登陆
            LoginReq loginReq= new LoginReq();
            ByteBuf byteBuf=message.getMsgBody();
            //得到用户名
            loginReq.setUserId(byteBuf.readInt());//4
            //得到密码
            byte[] pass = new byte[8];//8
            byteBuf.readBytes(pass);
            loginReq.setPassWord(new String(pass,"GBK"));
            //得到ip
            byte[] ip = new byte[32];//8
            byteBuf.readBytes(ip);
            loginReq.setDownlinkip(new String(ip,"GBK"));
            //得到端口号
            loginReq.setDownlinkport(byteBuf.readShort());
            //去数据库查询,如果有的话返回true没有的话 TODO

            // TODO 从数据库或者缓存中查询验证这个ip和密码 flag 成功 true
            if(true){

                /*
                Message msgRep = new Message(JTConstant.UP_CONNECT_RSP);
                //输出数据
                ByteBuf rspBuf = Unpooled.buffer(Message.MSG_FIX_LENGTH+5);
                //数据体
                ByteBuf bodybuffer = Unpooled.buffer(5);
                bodybuffer.writeByte(0x00);
                //校验码，临时写死
                bodybuffer.writeInt(1111);

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
                rspBuf.writeBytes(headbuf).writeBytes(bodybuffer);
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
*/


                String response = "I am ok!";
                ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
                encoded.writeBytes(response.getBytes());
                ctx.write(encoded);
                ctx.flush();
            }else {

                Message msgRep = new Message(JTConstant.UP_CONNECT_RSP);
                //输出数据
                ByteBuf rspBuf = Unpooled.buffer(Message.MSG_FIX_LENGTH+5);
                //数据体
                ByteBuf bodybuffer = Unpooled.buffer(5);
                /**
                 * 0x00:成功;
                 0x01:IP 地址不正确;
                 0x02:接入码不正确;
                 0x03:用户没用注册;
                 0x04:密码错误;
                 0x05:资源紧张,
                 稍后再连接(已经占 用);
                 0x06:其他。
                 */
            //TODO 给出具体的原因写入返回的信息中
                bodybuffer.writeByte(0x06);
                //校验码，临时写死
                bodybuffer.writeInt(1111);

                //数据头
                ByteBuf headbuf = Unpooled.buffer(22);
                headbuf.writeInt(rspBuf.capacity()-1);
                headbuf.writeInt(msgRep.getMsgSn());
                headbuf.writeShort((short) msgRep.getMsgId());
                headbuf.writeInt(msgRep.getMsgGesscenterId());
                headbuf.writeBytes(msgRep.getVersionFlag());
                headbuf.writeByte(0);
                headbuf.writeInt(10);

                rspBuf.writeBytes(headbuf).writeBytes(bodybuffer);
                ByteBuf finalBuf=rspBuf.copy();
                byte[] b = finalBuf.array();
                finalBuf.getBytes(0, b);
                int crcvalue= CRC16CCITT.crc16(b);
                finalBuf.writeShort(crcvalue);

                //转义
                byte[] bytes =finalBuf.copy().array();
                ByteBuf formatBuf = Unpooled.buffer(finalBuf.readableBytes());
                CRC16CCITT.formatBuffer(bytes, formatBuf);
                ByteBuf buf=Unpooled.buffer(formatBuf.readableBytes()+2);
                buf.writeByte(Message.MSG_HEAD);
                buf.writeBytes(formatBuf);
                buf.writeByte(Message.MSG_TALL);
                ctx.write(buf);

            }

        }else {
            //交个下一个handler处理数据
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("LoginHandler.channelReadComplete");
        ctx.flush();
    }


}