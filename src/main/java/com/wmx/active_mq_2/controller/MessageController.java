package com.wmx.active_mq_2.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

/**
 * jms 消息控制层
 */
@RestController
@RequestMapping("jms")
public class MessageController {
    /**
     * spring 的 JmsTemplate 是自动配置，可以直接注入使用，如同 JdbcTemplate 一样，非常方便，封装好了 API，直接调用即可
     */

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * JmsMessagingTemplate 对 JmsTemplate 进行了封装
     */
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 请求地址：http://localhost:8080/jms/sendMessage1?message=谢三哥
     *
     * @param message
     * @return
     */
    @GetMapping("sendMessage1")
    public String sendMessage1(String message) {
        String formatMessage = messageFormat(message);
        Destination destination = new ActiveMQQueue("my-queue");/**创建消息队列，自定义队列名称*/
        /**convertAndSend(D destination, Object payload):转换与发送消息，destination：目的地，payload：待发送的消息，底层调用 send 方法
         * send(D destination, Message<?> message)：发送消息，convertAndSend 方法会将原始消息加入消息头转换成真正能发送的消息（Message）
         * 支持发送的消息类型有：String, byte array, Map<String,?>, Serializable object.
         * 如下所示如果直接传 jsonNodes，则会抛异常，因为不支持 jsonNodes
         * 待发送的消息不能为 null，也不建议为空，否则接收端默认会抛异常
         */
        jmsTemplate.convertAndSend(destination, formatMessage);
        return formatMessage;
    }

    /**
     * 发送消息。请求地址：http://localhost:8080/jms/sendMessage2?message=张三哥
     *
     * @param message：带发送的消息
     * @return
     */
    @GetMapping("sendMessage2")
    public String sendMessage2(String message) {
        String formatMessage = messageFormat(message);
        Destination destination = new ActiveMQQueue("my-queue2");/**创建消息队列，自定义队列名称*/
        jmsMessagingTemplate.convertAndSend(destination, formatMessage);/**转换与发送消息*/
        return formatMessage;
    }

    /**
     * 将待发送的消息先进行 json 格式化一下，便于传输与取值。
     *
     * @param message ：用户待发送的原始消息
     * @return
     */
    private String messageFormat(String message) {
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode jsonNodes = jsonNodeFactory.objectNode();
        jsonNodes.put("message", message);//message 为 null 时，照样可以 put
        jsonNodes.put("status", 200);
        jsonNodes.put("timeStamp", System.currentTimeMillis());
        return jsonNodes.toString();
    }
}
