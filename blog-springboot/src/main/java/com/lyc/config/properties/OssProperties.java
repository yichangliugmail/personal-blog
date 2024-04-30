package com.lyc.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 阿里云对象存储
 * @Author liuYichang
 * @Date 2024/4/30 11:08
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.oss")
public class OssProperties {

    /**
     * 地域节点
     */
    private String endpoint;

    /**
     * 公钥
     */
    private String accessKeyId;

    /**
     * 私钥
     */
    private String accessKeySecret;

    /**
     * 存储桶名
     */
    private String bucketName;

    /**
     * 文件访问域名
     */
    private String url;
}
