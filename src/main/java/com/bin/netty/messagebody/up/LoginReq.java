package com.bin.netty.messagebody.up;

import java.io.Serializable;

/**
 * Created by zhangbin on 16/10/20.
 */
public class LoginReq implements Serializable{
    private static final long serialVersionUID = 5300716328017161678L;

    //用户名
    private int userId;
    //密码
    private String passWord;
    //下级平台提供对应的从链路服务端 IP 地址
    private String downlinkip;
    //下级平台提供对应的从链路服务器端口号
    private int downlinkport;

    public int getUserId() {
        return userId;
    }

    public LoginReq setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public LoginReq setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getDownlinkip() {
        return downlinkip;
    }

    public LoginReq setDownlinkip(String downlinkip) {
        this.downlinkip = downlinkip;
        return this;
    }

    public int getDownlinkport() {
        return downlinkport;
    }

    public LoginReq setDownlinkport(int downlinkport) {
        this.downlinkport = downlinkport;
        return this;
    }
}
