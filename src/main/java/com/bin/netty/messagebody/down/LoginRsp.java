package com.bin.netty.messagebody.down;

import java.io.Serializable;

/**
 * Created by zhangbin on 16/10/20.
 */
public class LoginRsp implements Serializable{
    private static final long serialVersionUID = 5300716328017161677L;

    /*
      验证结果,定义如下:
     0x00:成功;
     0x01:IP 地址不正确; 0x02:接入码不正确; 0x03:用户没用注册; 0x04:密码错误; 0x05:资源紧张,稍后再连接(已经占 用);
     0x06:其他。
     */
    private int resul;
    //校验码
    private int verifycode;

    public int getResul() {
        return resul;
    }

    public LoginRsp setResul(int resul) {
        this.resul = resul;
        return this;
    }

    public int getVerifycode() {
        return verifycode;
    }

    public LoginRsp setVerifycode(int verifycode) {
        this.verifycode = verifycode;
        return this;
    }
}
