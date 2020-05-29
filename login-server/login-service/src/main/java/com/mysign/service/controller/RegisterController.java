package com.mysign.service.controller;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.po.formValid;
import com.mysign.service.po.testData;
import com.mysign.service.vo.ExceptionEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/8 22:03
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    com.mysign.service.service.registerService registerService;

    @PostMapping("register")
    public ResponseEntity<Void> register(
            @RequestParam("user") String user,
            @RequestParam("password") String password,
            @RequestParam("password1") String password1,
            @RequestParam("value") String role
    ) {
        this.registerService.registerRole1(user,password, password1, role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("selectRole")
    public ResponseEntity<Boolean> selectRole(
            @RequestParam("user") String user,
            @RequestParam("password") String password,
            @RequestParam("password1") String password1,
            @RequestParam("value") String role
    ) {
        return ResponseEntity.ok(registerService.selectRole(user,password, password1,role));
    }

    //@RequestParam（通过字符串中解析出参数）
//@RequestBody（从请求体中获取参数）。
    @PostMapping("studentRole")
    @ResponseBody
    public Boolean registerStudent(@RequestBody testData testData) {
        if (ObjectUtils.isNotEmpty(testData)) {

            if (ObjectUtils.allNotNull(testData.getFormValid(), testData.getUser())) {
                return registerService.addStudent(testData.getFormValid(), testData.getUser());
            }
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);

    }


    @PostMapping("teacherRole")
    @ResponseBody
    public ResponseEntity<Boolean> registerTeacher(@RequestBody testData testData) {
        if (ObjectUtils.isNotEmpty(testData)) {
            if (ObjectUtils.allNotNull(testData.getFormValid(), testData.getUser())) {
                return ResponseEntity.ok(registerService.addTeacher(testData.getFormValid(), testData.getUser()));
            }
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);

    }

}
