package com.mysign.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/10 16:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mysign.service.mapper")
public class RegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class);
    }
}
