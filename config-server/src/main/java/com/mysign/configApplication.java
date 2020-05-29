package com.mysign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/6 9:42
 */
@SpringBootApplication
@EnableConfigServer
public class configApplication {
    public static void main(String[] args) {
        SpringApplication.run(configApplication.class);
    }
}
