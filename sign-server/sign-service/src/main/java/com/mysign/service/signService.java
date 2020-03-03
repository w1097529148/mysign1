package com.mysign.service;

import com.mysign.mapper.signMapper;
import com.mysign.mapper.teacherSignMapper;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import com.mysign.sign.signStudent;
import com.mysign.sign.signTeacher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:23
 */
@Service
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
    public Boolean release(String teacherId, Integer curseId, Double pointX, Double pointY) {
        if (StringUtils.isNotBlank(teacherId) && curseId != null && pointX != null && pointY != null) {
        java.util.Date date = new java.util.Date();

        Date date1=new Date(date.getTime());
        Timestamp timestamp = new Timestamp(date1.getTime()+8*60*60*1000);//确保存入数据库的是北京时间


            signTeacher signTeacher = new signTeacher(teacherId, curseId, timestamp, pointX, pointY);
            int insert = teacherSignMapper.insert(signTeacher);
            System.out.println("insert = " + insert);
            if (insert > 0)
                return true;
        }
        System.out.println("我出来了");
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
            Timestamp timestamp = new Timestamp(date1.getTime() + 8 * 60 * 60 * 1000);//确保存入数据库的是北京时间

            signStudent signStudent=new signStudent(studentId,curseId,timestamp,true,pointX,pointY);
            int insert = signMapper.insert(signStudent);
            System.out.println("insert = " + insert);
            if(insert>0)
                return true;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
}
