package com.mysign;

import com.mysign.mapper.signMapper;
import com.mysign.mapper.teacherSignMapper;
import com.mysign.sign.signStudent;
import com.mysign.sign.signTeacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:22
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class service {

    @Autowired
   signMapper signMapper;
@Autowired
    teacherSignMapper teacherSignMapper;
    @Test
    public void test(){
        List<signStudent> signTables = signMapper.selectAll();
        for (signStudent signStudent : signTables) {
            System.out.println(signStudent);
        }
    }
    @Test
    public void test1(){
        List<signTeacher> signTeachers = teacherSignMapper.selectAll();
        for (signTeacher signTeacher : signTeachers) {
            System.out.println("signTeacher = " + signTeacher);

        }

    }
}
