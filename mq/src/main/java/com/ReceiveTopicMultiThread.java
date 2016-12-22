package com;

/**
 * Created by zhangbin on 16/11/21.
 */
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class ReceiveTopicMultiThread implements Runnable {


    private String threadName;


    public ReceiveTopicMultiThread(String threadName) {
// TODO Auto-generated constructor stub
        this.threadName = threadName;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        ReceiveTopicMultiThread receive1 = new ReceiveTopicMultiThread(
                "thread1");
        ReceiveTopicMultiThread receive2 = new ReceiveTopicMultiThread(
                "thread2");
        ReceiveTopicMultiThread receive3 = new ReceiveTopicMultiThread(
                "thread3");
        Thread thread1 = new Thread(receive1);
        Thread thread2 = new Thread(receive2);
        Thread thread3 = new Thread(receive3);
        thread1.start();
        thread2.start();
        thread3.start();
    }


    public void run() {
// TODO Auto-generated method stub
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("FirstTopic");
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {


                    TextMessage tm = (TextMessage) message;
                    try {
                        System.out.println("线程" + threadName + "收到消息:"
                                + tm.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}