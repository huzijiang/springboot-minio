package com.xirm.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/3 13:24
 */
@Data
@Component
@ConfigurationProperties(prefix ="minio")
public class MinioConfig {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @Bean
    public MinioClient minioClient(){
        MinioClient minioClient=MinioClient.builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
        return minioClient;
    }

}
