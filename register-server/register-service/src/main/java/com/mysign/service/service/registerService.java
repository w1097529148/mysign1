package com.mysign.service.service;

import com.mysign.service.mapper.registerMapper;
import com.mysign.service.po.student;
import com.mysign.service.po.studentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/10 16:27
 */

@Service
public class registerService {

    @Autowired
    registerMapper registerMapper;
    public void addStdeuntInfo(studentInfo studentInfo){
        int b = registerMapper.addStudentInfo(studentInfo);
       String b1= b>0?"成功":"失败";
        System.out.println(b1);
    }
}
