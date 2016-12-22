package com.bin.netty.messagebody.up;

import java.io.Serializable;

/**
 * Created by zhangbin on 16/10/21.
 */
public class DynPosition implements Serializable {
    private static final long serialVersionUID = 5300716328017161675L;

    //加密标识:1-已加密,0-未加密
    private int excrypt;
    //dmyy
    private int date;
    //hms
    private int time;
    //log
    private double lon;
    private double lat;
    //速度
    private double vec1;
    //速度
    private double vec2;
    //里程数
    private double vec3;
    //方向 0-359
    private int direction;
    //haiba
    private int altitude;
    //车辆状态
    private int state;
    //报警状态
    private int alarm;

    public int getExcrypt() {
        return excrypt;
    }

    public DynPosition setExcrypt(int excrypt) {
        this.excrypt = excrypt;
        return this;
    }

    public int getDate() {
        return date;
    }

    public DynPosition setDate(int date) {
        this.date = date;
        return this;
    }

    public int getTime() {
        return time;
    }

    public DynPosition setTime(int time) {
        this.time = time;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public DynPosition setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public DynPosition setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getVec1() {
        return vec1;
    }

    public DynPosition setVec1(double vec1) {
        this.vec1 = vec1;
        return this;
    }

    public double getVec2() {
        return vec2;
    }

    public DynPosition setVec2(double vec2) {
        this.vec2 = vec2;
        return this;
    }

    public double getVec3() {
        return vec3;
    }

    public DynPosition setVec3(double vec3) {
        this.vec3 = vec3;
        return this;
    }

    public int getDirection() {
        return direction;
    }

    public DynPosition setDirection(int direction) {
        this.direction = direction;
        return this;
    }

    public int getAltitude() {
        return altitude;
    }

    public DynPosition setAltitude(int altitude) {
        this.altitude = altitude;
        return this;
    }

    public int getState() {
        return state;
    }

    public DynPosition setState(int state) {
        this.state = state;
        return this;
    }

    public int getAlarm() {
        return alarm;
    }

    public DynPosition setAlarm(int alarm) {
        this.alarm = alarm;
        return this;
    }
}
