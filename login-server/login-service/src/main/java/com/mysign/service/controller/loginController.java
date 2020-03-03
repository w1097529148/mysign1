package com.mysign.service.controller;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.curseReceiver;
import com.mysign.service.po.curse;
import com.mysign.service.po.teacherInfo;
import com.mysign.service.service.loginService;
import com.mysign.service.vo.ExceptionEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("login")
@RestController
public class loginController {

    @Autowired
    loginService loginService;

    @Autowired
    curseReceiver curseReceiver;
/**
*@Description: 登录
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/13 15:33
*/
    @GetMapping("login")
    public ResponseEntity<Map<Object, Object>> login(
            @RequestParam(name = "studentId") String studentId,
            @RequestParam(name = "pwd") String pwd,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "code") String Code

    ) {
        return ResponseEntity.ok(loginService.getMainPage(studentId, pwd, value));
    }



    @GetMapping("getStudentMessages")
        public ResponseEntity<Map<Object,Object>> getMessages(
                @RequestParam(value = "studentId",required = false) String studentId,
                @RequestParam(value = "teacherId",required = false) String teacherId

        ) {
        System.out.println(studentId);
        System.out.println(teacherId);
                return ResponseEntity.ok(loginService.getMessages(studentId,teacherId));
        }



    /**
    *@Description: 获取教师详细信息
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/13 15:33
    */
    @GetMapping("getTeacherMessage")
    public ResponseEntity<teacherInfo> getTeacherMessage(
            @RequestParam("teacherId") String teacherId
    ) {
        return ResponseEntity.ok(loginService.getTeacherMessage(teacherId));
    }
}
