package com.bin.netty;

import io.netty.buffer.ByteBuf;

public class CRC16CCITT {

    public static int crc16(byte[] bytes){
        int crc = 0xFFFF;
        for (int j = 0; j < bytes.length ; j++) {
            crc = ((crc  >>> 8) | (crc  << 8) )& 0xffff;
            crc ^= (bytes[j] & 0xff);//byte to int, trunc sign
            crc ^= ((crc & 0xff) >> 4);
            crc ^= (crc << 12) & 0xffff;
            crc ^= ((crc & 0xFF) << 5) & 0xffff;
        }
        crc &= 0xffff;
        return crc;
    }

    /**
     * 对数据进行转义
     * @param bytes
     * @param formatBuffer
     */
    public static void formatBuffer(byte[] bytes, ByteBuf formatBuffer) {
        for (byte b : bytes) {
            switch (b) {
                case 0x5b:
                    byte[] formatByte0x5b = new byte[2];
                    formatByte0x5b[0] = 0x5a;
                    formatByte0x5b[1] = 0x01;
                    formatBuffer.writeBytes(formatByte0x5b);
                    break;
                case 0x5a:
                    byte[] formatByte0x5a = new byte[2];
                    formatByte0x5a[0] = 0x5a;
                    formatByte0x5a[1] = 0x02;
                    formatBuffer.writeBytes(formatByte0x5a);
                    break;
                case 0x5d:
                    byte[] formatByte0x5d = new byte[2];
                    formatByte0x5d[0] = 0x5e;
                    formatByte0x5d[1] = 0x01;
                    formatBuffer.writeBytes(formatByte0x5d);
                    break;
                case 0x5e:
                    byte[] formatByte0x5e = new byte[2];
                    formatByte0x5e[0] = 0x5e;
                    formatByte0x5e[1] = 0x02;
                    formatBuffer.writeBytes(formatByte0x5e);
                    break;
                default:
                    formatBuffer.writeByte(b);
                    break;
            }
        }
    }

    /**
     * 补全位数不够的定长参数 有些定长参数，实际值长度不够，在后面补0x00
     * @param length
     * @param pwdByte
     * @return
     */
    public static byte[] getBytesWithLengthAfter(int length, byte[] pwdByte){
        byte[] lengthByte = new byte[length];
        for (int i = 0; i < pwdByte.length; i++) {
            lengthByte[i] = pwdByte[i];
        }
        for (int i = 0; i < (length - pwdByte.length); i++) {
            lengthByte[pwdByte.length + i] = 0x00;
        }
        return lengthByte;
    }



}