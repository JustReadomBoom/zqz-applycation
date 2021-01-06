package com.zqz.service.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zqz
 * @Description: 阿里云配置
 * @Date: Created in 3:45 PM 2019/12/19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliYunProperties {
    private String secretKey;

    private String secretId;

    private String bucket;

    private String intranet;

    private String extranet;

    private String filePath;

    private String parentPath;
}
