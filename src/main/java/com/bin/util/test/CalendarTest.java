package com.bin.util.test;

import java.util.Calendar;

/**
 * Created by zhangbin on 16/10/21.
 */
public class CalendarTest {

    public static void main(String[] args){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR,4);

        Calendar calendar1=Calendar.getInstance();

        System.out.println("时间比较：" + calendar.compareTo(calendar1));

    }
}
