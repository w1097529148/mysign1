package com.mysign.controller;

import com.mysign.service.signService;
import com.mysign.sign.signTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:23
 */
@RestController
@RequestMapping("sign")
public class signController {
    @Autowired
    signService signService;

    /**
    *@Description:  教师发布课程
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/29 9:26
    */
    @PostMapping("release")
    @ResponseBody
    public ResponseEntity<Boolean> teacherRelease(
            @RequestParam("teacherId")String teacherId,
            @RequestParam("curseId")Integer curseId,
            @RequestParam("pointX")Double pointX,
            @RequestParam("pointY")Double pointY
    ){
        return ResponseEntity.ok(signService.release(teacherId,curseId,pointX,pointY));
    }
/**
*@Description: 判断学生是否能签到（根据当前日期和课程id去教师签到表查询，为避免冲突取最后一条数据，则为最新的课程）
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/29 9:26
*/
    @PostMapping("sign")
    @ResponseBody
    public ResponseEntity<signTeacher> studentSign(
            @RequestParam("studentId")String studentId,
            @RequestParam("curseId")Integer curseId
    ){
        return ResponseEntity.ok(signService.studentSign(studentId,curseId));
    }
/**
*@Description: 学生签到
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/29 11:27
*/
    @PostMapping("signInsert")
    @ResponseBody
    public ResponseEntity<Boolean> stuSign(
            @RequestParam("studentId")String studentId,
            @RequestParam("curseId")Integer curseId,
            @RequestParam("teacherSignId")Integer teacherSignId,
            @RequestParam("pointX")Double pointX,
            @RequestParam("pointY")Double pointY
    ){
        return ResponseEntity.ok(signService.stuSign(studentId,curseId,teacherSignId,pointX,pointY));
    }

}
