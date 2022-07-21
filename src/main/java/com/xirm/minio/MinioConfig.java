package com.xirm.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huzj
 * @version 1.0
 * @date 2022/7/3 13:24
 */
@Configuration
public class MinioConfig {

//    @Value("${fileserver.minio.server.host}")
//    private String host;
//
//    @Value("${fileserver.minio.server.secure}")
//    private boolean secure;
//
//    @Value("${fileserver.minio.server.port}")
//    private int port;
//
//    @Value("${fileserver.minio.access-key}")
//    private String accessKey;
//
//    @Value("${fileserver.minio.secret-key}")
//    private String secretKey;
//
//    @Value("${fileserver.minio.region}")
//    private String region;
//
//    @Bean
//    public MinioClient minioClient() throws Exception {
//        return new MinioClient(host, port, accessKey, secretKey, region, secure);
//    }
}
