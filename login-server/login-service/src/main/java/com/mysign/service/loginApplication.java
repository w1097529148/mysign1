package com.mysign.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.FormContentFilter;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mysign.service.mapper")
@Import( FormContentFilter.class )//处理put delete请求的过滤器
@EnableFeignClients
public class loginApplication {
    public static void main(String[] args) {
        SpringApplication.run(loginApplication.class);
    }
}
