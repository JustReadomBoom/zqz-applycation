package com.zqz;

import com.rabbitmq.client.*;
import com.zqz.service.mq.AliyunCredentialsProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: MQTest
 * @Date: Created in 9:13 2022/3/2
 */
@Slf4j
public class MQTest {

    private static final String HOST = "amqp-cn-2r42l8ybr003.mq-amqp.cn-hangzhou-249959-a.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI4G7zdgkK2FtyG7a9GBWT";
    private static final String ACCESS_KEY_SECRET = "du3emvRgapEkHaUh5ikTNGWEshZugJ";
    private static final String SECURITY_TOKEN = null;
    private static final String INSTANCE_ID = "amqp-cn-2r42l8ybr003";
    private static final String V_HOST_NAME = "zqz_test_host";
    private static final String EXCHANGE_NAME = "zqz_test_exchange";
    private static final String EXCHANGE_TYPE = "topic";
    private static final String QUEUE_NAME = "zqz_test_queue2";
    private static final String BINDING_KEY = "zqz_test_bind2";


    @Test
    public void testProduct() {
        Connection connection;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            // 设置接入点，在消息队列RabbitMQ版控制台实例详情页面查看。
            factory.setHost(HOST);
            // ${instanceId}为实例ID，在消息队列RabbitMQ版控制台实例详情页面查看。
            // ${AccessKey}阿里云身份验证，在阿里云控制台创建。
            // ${SecretKey}阿里云身份验证，在阿里云控制台创建。
            factory.setCredentialsProvider(new AliyunCredentialsProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET, SECURITY_TOKEN, INSTANCE_ID));
            //设置为true，开启Connection自动恢复功能；设置为false，关闭Connection自动恢复功能。
            factory.setAutomaticRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(5000);
            // 设置Vhost名称，请确保已在消息队列RabbitMQ版控制台上创建完成。
            factory.setVirtualHost(V_HOST_NAME);
            // 默认端口，非加密端口5672，加密端口5671。
            factory.setPort(5672);
            // 基于网络环境合理设置超时时间。
            factory.setConnectionTimeout(30 * 1000);
            factory.setHandshakeTimeout(30 * 1000);
            factory.setShutdownTimeout(0);
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true, false, false, null);
            channel.queueDeclare(QUEUE_NAME, true, false, false, new HashMap<>());
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);
            // 开始发送消息。
            for (int i = 0; i < 5; i++) {
                // ${ExchangeName}必须在消息队列RabbitMQ版控制台上已存在，并且Exchange的类型与控制台上的类型一致。
                // ${BindingKey}根据业务需求填入相应的BindingKey。
                AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().messageId(UUID.randomUUID().toString()).build();
                channel.basicPublish(EXCHANGE_NAME, BINDING_KEY, true, props, ("测试消息" + i).getBytes(StandardCharsets.UTF_8));
                log.info(">>>>>>>> 已发送消息");
            }
            connection.close();
        } catch (Exception e) {
            log.error("MQ product error:{}", e.getMessage(), e);
        }
    }

}
