package com.zqz.service.mq;

import com.alibaba.mq.amqp.utils.UserUtils;
import com.rabbitmq.client.impl.CredentialsProvider;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AliyunCredentialsProvider
 * @Date: Created in 9:09 2022/3/2
 */
public class AliyunCredentialsProvider implements CredentialsProvider {

    /**
     * Access Key ID.
     */
    private final String accessKeyId;
    /**
     * Access Key Secret.
     */
    private final String accessKeySecret;
    /**
     * security temp token. (optional)
     */
    private final String securityToken;
    /**
     * 实例ID（从消息队列RabbitMQ版控制台获取）。
     */
    private final String instanceId;


    public AliyunCredentialsProvider(final String accessKeyId, final String accessKeySecret,
                                     final String instanceId) {
        this(accessKeyId, accessKeySecret, null, instanceId);
    }

    public AliyunCredentialsProvider(final String accessKeyId, final String accessKeySecret,
                                     final String securityToken, final String instanceId) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
        this.instanceId = instanceId;
    }


    @Override
    public String getUsername() {
        if (StringUtils.isNotEmpty(securityToken)) {
            return UserUtils.getUserName(accessKeyId, instanceId, securityToken);
        } else {
            return UserUtils.getUserName(accessKeyId, instanceId);
        }
    }

    @Override
    public String getPassword() {
        try {
            return UserUtils.getPassord(accessKeySecret);
        } catch (InvalidKeyException e) {
            //todo
        } catch (Exception ce) {
            //todo
        }
        return null;
    }
}
