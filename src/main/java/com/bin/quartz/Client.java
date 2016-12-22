package com.bin.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangbin on 16/10/26.
 */


public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        initquartz();
    }

    private static void initquartz() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:spring/spring-dao.xml", "classpath:spring/spring-quartz.xml"});
    }
}
