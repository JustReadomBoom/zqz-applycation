package com.zqz.service.mq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: ConsumerService
 * @Date: Created in 9:34 2022/3/2
 */

@Service
@Slf4j
public class ConsumerService implements InitializingBean, DisposableBean {
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
    private static final String CONSUMER_TAG = "ConsumerTag";

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("===========================================================================");
        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            // 设置接入点，在消息队列RabbitMQ版控制台实例详情页面查看。
            factory.setHost(HOST);
            // ${instanceId}为实例ID，在消息队列RabbitMQ版控制台概览页面查看。
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
            factory.setConnectionTimeout(300 * 1000);
            factory.setHandshakeTimeout(300 * 1000);
            factory.setShutdownTimeout(0);
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            // 创建${ExchangeName}，该Exchange必须在消息队列RabbitMQ版控制台上已存在，并且Exchange的类型与控制台上的类型一致。
            AMQP.Exchange.DeclareOk exchangeDeclareOk = channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true, false, false, null);
            // 创建${QueueName} ，该Queue必须在消息队列RabbitMQ版控制台上已存在。
            AMQP.Queue.DeclareOk queueDeclareOk = channel.queueDeclare(QUEUE_NAME, true, false, false, new HashMap<>());
            // Queue与Exchange进行绑定，并确认绑定的BindingKeyTest。
            AMQP.Queue.BindOk bindOk = channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);
            // 开始消费消息。
            channel.basicConsume(QUEUE_NAME, false, CONSUMER_TAG, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //接收到的消息，进行业务逻辑处理。
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    log.info("Received:{}, deliveryTag:{}, messageId:{} ", new String(body, StandardCharsets.UTF_8), envelope.getDeliveryTag(), properties.getMessageId());
                }
            });
            log.info("djsajdoasjdsojso;ifjdskfsdfsdlkfjsdlfksdklgjsdgjfjgfdijgrkl");
            connection.close();
        } catch (Exception e) {
            log.error("MQ consumer error:{}", e.getMessage(), e);
        }
    }
}
