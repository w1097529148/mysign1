package com.mysign.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysign.mapper.signMapper;
import com.mysign.mapper.teacherSignMapper;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import com.mysign.sign.signStudent;
import com.mysign.sign.signTeacher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:23
 */
@Service
@Slf4j
public class signService {
    @Autowired
    teacherSignMapper teacherSignMapper;

    /**
    *@Description: 教师发布
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/29 9:28
    */
    public Boolean release(String teacherId, Integer curseId, Float pointX, Float pointY, String startTime, String endTime) {
        if (StringUtils.isNotBlank(teacherId) && curseId != null && pointX != null && pointY != null&&startTime!=null&&endTime!=null) {
                TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
            Date startDate=null;
            Date endDate=null;
            String date1 = dateFormat.format(date).toString() + startTime;
            log.info("开始时间：{}",date1);
            String date2 = dateFormat.format(date).toString() + endTime;
            log.info("结束时间：{}",date2);
            try {
                startDate = dateFormat1.parse(date1);
                log.info("转换后的日期：{}",startDate);
                endDate = dateFormat1.parse(date2);
                log.info("转换后的日期：{}",endDate);
            }catch (Exception e){
                log.info("日期转换异常");
                throw new RuntimeException(e);
            }
                signTeacher teacher = new signTeacher();
                teacher.setCurseId(curseId);
                teacher.setTeacherId(teacherId);
                teacher.setStartTime(startDate);
                teacher.setEndTime(endDate);
                int count = teacherSignMapper.selectCount(teacher);
                if (count > 0) {
                    throw new MySignException(ExceptionEnum.SIGN_MESSAGE_IS_NOT_EXISTS);
                }
                signTeacher signTeacher = new signTeacher();
                signTeacher.setTeacherId(teacherId);
                signTeacher.setEndTime(endDate);
                signTeacher.setStartTime(startDate);
                signTeacher.setSignTime(date);
                signTeacher.setCurseId(curseId);
                signTeacher.setSignCoordinatesX(pointX);
                signTeacher.setSignCoordinatesY(pointY);
                log.info("开始进行添加操作-----添加签到信息：{}", signTeacher);
                int insert = teacherSignMapper.insert(signTeacher);
                if (insert > 0) {
                    log.info("---------------添加成功--------------------");
                    return true;
                }
                log.info("---------------添加失败--------------------");
        }
        log.info("------------------------空字段---------------------");
       throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
/**
*@Description: 学生签到
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/29 9:28
*/


    public signTeacher studentSign(String studentId, Integer curseId) {
        if (StringUtils.isNotBlank(studentId) && curseId != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
                String format = simpleDateFormat.format(date);
                System.out.println("format = " + format);
               signTeacher signTeacher= teacherSignMapper.selectByCurseIdAndTime(curseId,"%"+format+"%");
            System.out.println(signTeacher);
               return signTeacher;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    /**
    *@Description: 学生签到
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/29 11:32
    */
    @Autowired
    signMapper signMapper;//注入学生签到Mapper
    public Boolean stuSign(String studentId, Integer curseId, Integer teacherSignId, Double pointX, Double pointY) {
        if (StringUtils.isNotBlank(studentId) && curseId != null && pointX != null && pointY != null&&teacherSignId!=null) {
            java.util.Date date = new java.util.Date();
            Date date1 = new Date(date.getTime());
            signStudent signStudent=new signStudent(studentId,curseId,date1,true,pointX,pointY);
            int insert = signMapper.insert(signStudent);
            System.out.println("insert = " + insert);
            if(insert>0)
                return true;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    public PageInfo getStudentSignMessage(String studentId, Integer pageSize, Integer pageNum) {
        if (StringUtils.isNotBlank(studentId)&&pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> signMessage = signMapper.getSignMessage(studentId);
            PageInfo objectPageInfo = new PageInfo(signMessage);
            return objectPageInfo;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    public PageInfo getStudentSignMessageByTeacherId(String teacherId,Integer pageNum,Integer pageSize){
        log.debug("传入的teacherId：{}",teacherId);
        log.debug("传入的pageNum为：{}",pageNum);
        log.debug("传入的pageSize为：{}",pageSize);
        if (StringUtils.isNotEmpty(teacherId)&&pageNum!=null&&pageSize!=null) {
            PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> message = teacherSignMapper.getStudentSignMessageByTeacherId(teacherId);
            PageInfo pageInfo = new PageInfo(message);
            if (pageInfo != null) {
                log.info("查询成功，返回结果集为", pageInfo);
                return pageInfo;
            }
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

   public PageInfo findStudentSignMessage(String curseName,String studentName,Integer PageSize,Integer PageNum){
        if (StringUtils.isNotEmpty(curseName)||StringUtils.isNotEmpty(studentName)){
            PageHelper.startPage(PageNum,PageSize);
            List<Map<String, Object>> studentSignMessage = teacherSignMapper.findStudentSignMessage(curseName, studentName);
            log.debug("查到数据为：{}",studentSignMessage);
            if (studentSignMessage!=null){
            PageInfo pageInfo = new PageInfo(studentSignMessage);
            if (pageInfo!=null){
                log.debug("返回数据为：{}",pageInfo);
                return pageInfo;
            }
            }
         throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
}
