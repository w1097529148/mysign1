package com.mysign.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/8 23:30
 */
@Configuration
public  class MD5Utils {
    public  String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());

            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);

        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public Boolean PasswordChecking(String password,String Md5password){
       return StringUtils.equals(getMD5(password),Md5password);
    }

}
