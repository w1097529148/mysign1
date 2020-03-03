package com.mysign.service;

import com.mysign.service.po.studentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/10 20:36
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    com.mysign.service.service.registerService registerService;
    @Test
    public void test(){
        studentInfo studentInfo=new studentInfo(null,"160510210","张二娃","default",new Date(1999999999),true,"四川省眉山市彭山区锦江大道1号","15555555555","2016级","2班","计算机专业","计算机科学与技术");
        registerService.addStdeuntInfo(studentInfo);
    }
}
