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
import java.util.Map;

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
    @Test
    public void getSignMessage(){
        List<Map<String, Object>> signMessage = signMapper.getSignMessage("160510201");
        for (Map<String, Object> map : signMessage) {
            System.out.println("map = " + map);
        }
    }

    @Test
    public void testGetStudentSignMessage(){
        List<Map<String, Object>> messageByTeacherId = teacherSignMapper.getStudentSignMessageByTeacherId("160510201");
        for (Map<String, Object> map : messageByTeacherId) {
            System.out.println("map = " + map);
        }
    }
    @Test
    public void t(){
        List<Map<String, Object>> test = teacherSignMapper.findStudentSignMessage(null,"张二娃");
        for (Map<String, Object> map : test) {
            System.out.println("map = " + map);
        }
    }
}
