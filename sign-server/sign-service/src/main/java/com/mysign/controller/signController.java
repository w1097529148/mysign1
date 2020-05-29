package com.mysign.controller;

import com.github.pagehelper.PageInfo;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.signService;
import com.mysign.service.vo.ExceptionEnum;
import com.mysign.sign.signTeacher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:23
 */
@RestController
@RequestMapping("sign")
@Slf4j
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
    public Boolean teacherRelease(
            @RequestParam("teacherId")String teacherId,
            @RequestParam("curseId")Integer curseId,
            @RequestParam("pointX")Float pointX,
            @RequestParam("pointY")Float pointY,
            String startTime,
            String endTime
    ){
        log.info("接收数据成功------发起添加签到请求");
        return signService.release(teacherId,curseId,pointX,pointY,startTime,endTime);
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

//    @GetMapping("getStudentSignMessage")
//        @ResponseBody
//    public List<signStudent> getStudentSignMessage(
//            @RequestParam("studentId")String studentId
//        ){
//        return signService.getStudentSignMessage(studentId);
//        }
    @GetMapping("getSignMessage")
    public PageInfo getSignMessage(String studentId, Integer pageSize, Integer pageNum){
        log.info("开始查询学生签到信息");
        log.info("学生学号：{}",studentId);
        log.info("当前页数：{}",pageNum);
        log.info("页面大小：{}",pageSize);
        if (StringUtils.isNotBlank(studentId)&&pageNum!=null&&pageSize!=null){
            PageInfo message = signService.getStudentSignMessage(studentId, pageNum, pageSize);
            log.info("返回列表：{}",message);
            return message;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    @GetMapping("getStudentSignMessage")
    public PageInfo getStudentSignMessageByTeacherId(String teacherId, Integer pageSize, Integer pageNum){
        log.info("开始查询学生签到信息");
        log.info("学生学号：{}",teacherId);
        log.info("当前页数：{}",pageNum);
        log.info("页面大小：{}",pageSize);
        if (StringUtils.isNotBlank(teacherId)&&pageNum!=null&&pageSize!=null){
            PageInfo message = signService.getStudentSignMessageByTeacherId(teacherId, pageSize, pageNum);
            log.info("返回列表：{}",message);
            return message;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
    @GetMapping("findStudentMessage")
    public PageInfo findStudentMessage(String curseName,String studentName,
                                       @RequestParam(required = true,defaultValue ="10") Integer PageSize,
                                       @RequestParam(required = true,defaultValue ="1")Integer PageNum){
        if (StringUtils.isNotBlank(curseName)||StringUtils.isNotBlank(studentName)){
            log.debug("传入curseName:{}",curseName);
            log.debug("传入studentName:{}",studentName);
            return signService.findStudentSignMessage(curseName, studentName, PageSize, PageNum);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

}
