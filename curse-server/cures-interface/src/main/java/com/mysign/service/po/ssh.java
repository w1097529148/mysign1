package com.mysign.service.po;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/5/17 9:49
 */
@Data
@ConfigurationProperties(prefix = "ssh")
@Configuration
public class ssh {
    private  String ip;

    private  String name;

    private  String password;

    private  Integer port;

    private  String filePath;
}
