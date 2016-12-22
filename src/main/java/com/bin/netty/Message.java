package com.bin.netty;


import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.util.Arrays;

/**
 * zhangbin
 * 转换对象
 * bytes->message; message-bytes
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 4398559115325723920L;

    public static final int MSG_HEAD = 0x5b;
    public static final int MSG_TALL = 0x5d;

    //报文中除数据体外，固定的数据长度
    public static final int MSG_FIX_LENGTH = 26;

    private static int internalMsgNo = 0;
    //数据长度(包括头标识、数据头、数据体和尾标识)
    private long msgLength;
    //业务数据类型
    private int msgId;
    //报文序列号 a
    /*
    a 占用四个字节,为发送信息的序列号,用于接收方检测是否有信息的丢失,
    上级平台 和下级平台接自己发送数据包的个数计数,互不影响。
    程序开始运行时等于零,发送第一帧 数据时开始计数,到最大数后自动归零。
     */
    private int msgSn;
    //下级平台接入码,上级平台给下级平台分配唯一标识码。
    private int msgGesscenterId;
    /*
    协议版本好标识,上下级平台之间采用的标准协议版 编号;
    长度为 3 个字节来表示,0x01 0x02 0x0F 标识 的版本号是 v1.2.15,以此类推。
     */
    private byte[] versionFlag = {0,0,1};

    //报文加密标识位b: 0表示报文不加密,1表示报文加 密
    private long encryptFlag=1;

    //数据加密的密匙,长度为 4 个字节
    private long encryptKey;
    //数据 CRC 校验码
    private int crcCode;

    //数据踢
    private ByteBuf msgBody;

    //下行报文标识，值为1时，代表发送的数据；默认为0，代表接收的报文
//  private int downFlag = 0;

    public Message(){}


    public Message(int msgId){
        //下行报文需要填充报文序列号
        synchronized((Integer)internalMsgNo) {
            if(internalMsgNo == Integer.MAX_VALUE){
                internalMsgNo = 0;
            }
        }
        this.msgSn = ++internalMsgNo;
        this.msgId = msgId;
        //this.downFlag = 1;
    }

    public ByteBuf getMsgBody() {
        return msgBody;
    }

    public Message setMsgBody(ByteBuf msgBody) {
        this.msgBody = msgBody;
        return this;
    }

    public static int getInternalMsgNo() {
        return internalMsgNo;
    }


    public static void setInternalMsgNo(int internalMsgNo) {
        Message.internalMsgNo = internalMsgNo;
    }


    public long getMsgLength() {
        return msgLength;
    }


    public void setMsgLength(long msgLength) {
        this.msgLength = msgLength;
    }


    public long getEncryptFlag() {
        return encryptFlag;
    }


    public void setEncryptFlag(long encryptFlag) {
        this.encryptFlag = encryptFlag;
    }


    public int getMsgGesscenterId() {
        return msgGesscenterId;
    }


    public void setMsgGesscenterId(int msgGesscenterId) {
        this.msgGesscenterId = msgGesscenterId;
    }
    public void setMsgGesscenterId(long msgGesscenterId) {
        this.msgGesscenterId = (int)msgGesscenterId;
    }


    public long getEncryptKey() {
        return encryptKey;
    }


    public void setEncryptKey(long encryptKey) {
        this.encryptKey = encryptKey;
    }


    public int getCrcCode() {
        return crcCode;
    }


    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }


    public int getMsgId() {
        return msgId;
    }


    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


    public int getMsgSn() {
        return msgSn;
    }


    public void setMsgSn(int msgSn) {
        this.msgSn = msgSn;
    }


    public byte[] getVersionFlag() {
        return versionFlag;
    }


    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }


    public static int getMsgHead() {
        return MSG_HEAD;
    }


    public static int getMsgTall() {
        return MSG_TALL;
    }


    public static int getMsgFixLength() {
        return MSG_FIX_LENGTH;
    }


    @Override
    public String toString() {
        return "Message [msgLength=" + msgLength + ", encryptFlag=" + encryptFlag + ", msgGesscenterId="
                + msgGesscenterId + ", encryptKey=" + encryptKey + ", crcCode=" + crcCode + ", msgId=" + msgId
                + ", msgSn=" + msgSn + ", msgBody=" + msgBody + ", versionFlag=" + Arrays.toString(versionFlag) + "]";
    }

}