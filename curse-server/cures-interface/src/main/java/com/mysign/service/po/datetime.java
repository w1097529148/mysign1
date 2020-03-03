package com.mysign.service.po;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/21 15:33
 */
@Data
@ConfigurationProperties(prefix = "datetime")
@Configuration
public class datetime {
    private  String zero;
    private String first;
    private  String second;
    private String third;
    private String fourth;

    private String fifth;
    private String sixth;
    private String seventh;
    private String eighth;

    private String ninth;
    private String tenth;
    private String  eleventh;
}
