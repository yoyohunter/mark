package com.bin.service.activemq;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by zhangbin on 16/11/17.
 */
@Component
public class QueueReceiver implements MessageListener {

    public void onMessage(Message message) {
        try {
            System.out.println("QueueReceiver1接收到消息:" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
