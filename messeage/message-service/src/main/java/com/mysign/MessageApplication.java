package com.mysign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 8:58
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mysign.mapper")
public class MessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class);
    }
}
