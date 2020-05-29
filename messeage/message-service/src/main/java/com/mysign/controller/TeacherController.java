package com.mysign.controller;

import com.alibaba.fastjson.JSONObject;
import com.mysign.Iservice.ITeacher;
import com.mysign.bean.StudentMessage;
import com.mysign.bean.TeacherMessage;
import com.mysign.bean.studentInfo;
import com.mysign.bean.teacherInfo;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 9:40
 */
@RestController
@RequestMapping("message")
@Slf4j
public class TeacherController {
    @Autowired
    private ITeacher iTeacher;
    /**
     *@Description: 查询未读的教师信息，用于批复学生请假
     *@Params [Id]
     *@Return java.util.List<com.mysign.bean.TeacherMessage>
     *@Author Mr.Li
     *@Date 2020/3/29 9:56
     */
    @GetMapping("getTeacherMessage")
    public List<TeacherMessage> getStudentMessage(
            @RequestParam("Id")Integer Id
    ){
        log.info("获取教师信息，教师id：{}",Id);
        return iTeacher.getStudentMessage(Id);
    }

    @GetMapping("agree")
    public Boolean agreeMessage(Integer messageId,String studentId,String teacherId){
        if (messageId!=null)
        return iTeacher.agreeMessage(messageId,studentId,teacherId);
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    @GetMapping("refuse")
    public Boolean agreeMessage(Integer messageId,String reason,String studentId,String teacherId){
        if (messageId!=null&& StringUtils.isNotBlank(reason))
            return iTeacher.refuseMessage(messageId,reason,studentId,teacherId);
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    @PostMapping("updateTeacherTitle")
    public Boolean updateTitle(@RequestBody String user){
        log.info("用户修改头像=========================");
        if (user!=null){
            log.info("传入的字符串为：{}",user);
            JSONObject jsonObject = JSONObject.parseObject(user);
            log.info("获取json对象为：{}",jsonObject);
            JSONObject user1 = jsonObject.getJSONObject("user");
            log.info("获取json对象为:{}",user1);
            teacherInfo teacherInfo = user1.toJavaObject(teacherInfo.class);
            log.info("传入对象：{}",teacherInfo);
            return iTeacher.updateTitle(teacherInfo);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
}
