package com.mysign.serviceImpl;

import com.mysign.Iservice.IStudent;
import com.mysign.bean.StudentMessage;
import com.mysign.bean.studentInfo;
import com.mysign.mapper.studentInfoMapper;
import com.mysign.mapper.studentMapper;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description T0D0
 * @Author Mr.Li
 * @Date 2020/3/29 9:39
 */
@Service
@Slf4j
public class StudentServiceImpl implements IStudent {
    @Autowired
    private studentMapper studentMapper;
    @Autowired
    private StudentMessage studentMessage;
    @Override
    public List<StudentMessage> getStudentMessage(Integer id) {
        if (id!=null){
            studentMessage.setStudentId(String.valueOf(id));
            studentMessage.setState(false);
            return studentMapper.select(studentMessage);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    @Override
    public Boolean addMessage(String studentId, String teacherId, String startdate, String startTime, String enddate, String endTime, String desc) {
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String substring = StringUtils.substring(startdate, 0, 10)+" "+startTime;
        System.out.println("substring = " + substring);
        String s = StringUtils.substring(enddate, 0, 10) + " " + endTime;
        System.out.println("s = " + s);
        Date parse = null;
        Date endparse=null;
        try {
            parse = sm.parse(s);
            endparse=sm.parse(substring);
            System.out.println("parse = " + parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer integer = studentMapper.addMessage(studentId, teacherId, new Date(), parse, endparse, desc);
        return integer>0;
    }
/**
*@Description: 修改头像
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/5/17 21:49
*/
@Autowired
    studentInfoMapper studentInfoMapper;
    @Override
    public Boolean updateTitle(studentInfo studentInfo) {
        if (studentInfo!=null){
            log.info("传入学生信息对象：{}",studentInfo.toString());
            return studentInfoMapper.updateByPrimaryKeySelective(studentInfo)>0;
        }
        log.info("传入空值");
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

}
