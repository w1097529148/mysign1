package com.mysign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.mysign.Iservice.IStudent;
import com.mysign.bean.StudentMessage;
import com.mysign.bean.studentInfo;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 9:40
 */
@RestController
@RequestMapping("message")
@Slf4j
public class StudentController {
    @Autowired
    private IStudent iStudent;
    /**
     *@Description: 根据id查询未读的学生信息 用于查看学生请假是否得到批准
     *@Params [Id]
     *@Return java.util.List<com.mysign.bean.StudentMessage>
     *@Author Mr.Li
     *@Date 2020/3/29 10:02
     */
    @GetMapping("getStudentMessage")
    public List<StudentMessage> getStudentMessage(
            @RequestParam("Id")Integer Id
    ){
        return iStudent.getStudentMessage(Id);
    }
    @GetMapping("leave")
public Boolean addMessage(@RequestParam("studentId") String studentId,
                          @RequestParam("teacherId") String teacherId,
                          @RequestParam("startdate") String startdate,
                          @RequestParam("starttime") String startTime,
                          @RequestParam("enddate")String enddate,
                          @RequestParam("endtime") String endTime,
                          @RequestParam("desc" )String desc){
       return iStudent.addMessage(studentId,teacherId,startdate,startTime,enddate,endTime,desc);
    }

@PostMapping("updateTitle")
    public Boolean updateTitle(@RequestBody String user){
    if (user!=null) {
        log.info("传入的字符串为：{}",user);
        JSONObject jsonObject = JSONObject.parseObject(user);
        log.info("获取json对象为：{}",jsonObject);
        JSONObject user1 = jsonObject.getJSONObject("user");
        log.info("获取json对象为:{}",user1);
        studentInfo studentInfo = user1.toJavaObject(studentInfo.class);
        log.info("传入对象：{}",studentInfo);
        return iStudent.updateTitle(studentInfo);
    }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
}

}
