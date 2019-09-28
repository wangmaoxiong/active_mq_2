package com.wmx.active_mq_2.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 */
@Component
public class MessageConsumer {

    /**
     * 当 jms 基础设施存在时，任何 bean 都可以用 @JmsListener 注解创建监听器端点，如果未定义 JmsListenerContainerFactory，则会自动配置默认值。
     * 默认情况下，默认工厂是事务性的。如果在存在 JtaTransactionManager 的基础结构中运行，则默认情况下它与侦听器容器相关联
     *
     * @param message
     */
    @JmsListener(destination = "my-queue")
    public void receiveMessage(String message) {
        System.err.println("接收到了消息: " + message);
    }

    /**
     * 可以同时监听任意多个消息队列，都会自动接收消息。
     *
     * @param message
     */
    @JmsListener(destination = "my-queue2")
    public void receiveMessage2(String message) {
        System.out.println("收到消息：" + message);
    }

}
