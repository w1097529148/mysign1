package com.mysign.service.controller;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.curseReceiver;
import com.mysign.service.po.teacherInfo;
import com.mysign.service.service.loginService;
import com.mysign.service.utils.AliSendSMS;
import com.mysign.service.utils.RedisUtil;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("login")
@RestController
@Slf4j
public class loginController {

    @Autowired
    loginService loginService;

    @Autowired
    curseReceiver curseReceiver;

    /**
     * @Description: 登录
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/13 15:33
     */
    @GetMapping("login")
    public ResponseEntity<Map<Object, Object>> login(
            @RequestParam(name = "studentId") String studentId,
            @RequestParam(name = "pwd") String pwd,
            @RequestParam(name = "value") String value
    ) {
        return ResponseEntity.ok(loginService.getMainPage(studentId, pwd, value));
    }


    @GetMapping("getStudentMessages")
    public Map<Object, Object> getMessages(
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "teacherId", required = false) String teacherId

    ) {
        log.info("学生信息初始化开始=======================");
        log.info("传入的老师id：{}",teacherId);
        log.info("传入的学生id：{}",studentId);
        return loginService.getMessages(studentId, teacherId);
    }


    /**
     * @Description: 获取教师详细信息
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/13 15:33
     */
    @GetMapping("getTeacherMessage")
    public ResponseEntity<teacherInfo> getTeacherMessage(
            @RequestParam("teacherId") String teacherId
    ) {
        return ResponseEntity.ok(loginService.getTeacherMessage(teacherId));
    }

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("update")
    public Boolean updatePassword(String phone, String code) {
        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)) {
//            System.out.println("phone = " + phone);
//            System.out.println("code = " + code);
            String messageCode = redisUtil.get(phone);
//            System.out.println("messageCode = " + messageCode);
            if (messageCode!=null)
            if (code.equals(messageCode)) {
                return true;
            } else {
                throw new MySignException(ExceptionEnum.CODE_IS_NOT_SAME);
            }
            throw new MySignException(ExceptionEnum.CODE_HAVE_GOT_OUT);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    @GetMapping("getCode")
    public Boolean getCode(String phone, String radio) {
        if (!StringUtils.isAllBlank(phone, radio)) {
            Boolean user = loginService.selectUserByPhoneAndRadio(phone, radio);
            if (user) {
//                String code = AliSendSMS.sendSMS(phone);
//                String message = phone +code;
                redisUtil.set(phone, "hello");
                return true;
            }
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
/**
*@Description: 密码重置
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/5/12 9:31
*/
    @PutMapping("putPassword")
    public Boolean putPassword( String password, String role,String phone) {
        if (!StringUtils.isAllBlank(password,role,phone)) {
            return loginService.putPassword(password, role,phone);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    public static void main(String[] args) {
        for (int j = 6; j <=74; j++) {
            String str="https://www.bilibili.com/video/BV1WE411p7yH?p="+j+"\n";
            System.out.println(str);
        }
    }
}
