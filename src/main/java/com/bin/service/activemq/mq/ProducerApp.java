package com.bin.service.activemq.mq;

/**
 * Created by zhangbin on 16/11/17.
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by outofmemory.cn on 14-8-26.
 * <p/>
 * activemq 生产者实例
 */
public class ProducerApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerApp.class);
    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String SUBJECT = "test-activemq-queue";

    public static void main(String[] args) throws JMSException {
        //初始化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        //获得连接
        Connection conn = connectionFactory.createConnection();
        //启动连接
        conn.start();

        //创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建队列
        Destination dest = session.createQueue(SUBJECT);
        //createTopic方法用来创建Topic
        //session.createTopic("TOPIC");

        //通过session可以创建消息的生产者
        MessageProducer producer = session.createProducer(dest);
        for (int i = 0; i < 100; i++) {
            //初始化一个mq消息
            TextMessage message = session.createTextMessage("hello active mq 中文" + i);
            //发送消息
            producer.send(message);
            LOGGER.debug("send message {}", i);
        }

        //关闭mq连接
        conn.close();
    }
}