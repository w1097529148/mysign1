package com.mysign.serviceImpl;

import com.mysign.Iservice.ITeacher;
import com.mysign.bean.StudentMessage;
import com.mysign.bean.TeacherMessage;
import com.mysign.bean.studentInfo;
import com.mysign.bean.teacherInfo;
import com.mysign.mapper.studentInfoMapper;
import com.mysign.mapper.studentMapper;
import com.mysign.mapper.teacherInfoMapper;
import com.mysign.mapper.teacherMapper;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 9:39
 */
@Service
@Slf4j
public class TeacherServiceImpl implements ITeacher {
    @Autowired
    private teacherMapper teacherMapper;
   @Autowired
    private   TeacherMessage teacherMessage;
    @Override
    public List<TeacherMessage> getStudentMessage(Integer id) {
        if (id!=null){
            System.out.println("id = " + id);
            String s = String.valueOf(id);
            System.out.println("s = " + s);
            teacherMessage.setTeacherId(s);
            teacherMessage.setState(false);
            System.out.println("teacherMessage = " + teacherMessage);
            List<TeacherMessage> select = teacherMapper.select(teacherMessage);
            if (!CollectionUtils.isEmpty(select))
                return select;
            return null;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
@Autowired
studentMapper studentMapper;
    @Transactional
    @Override
    public Boolean agreeMessage(Integer messageId, String studentId, String teacherId) {
        if (messageId!=null){
            TeacherMessage teacherMessage = new TeacherMessage();
            teacherMessage.setId(messageId);
            teacherMessage.setState(true);
            int i = teacherMapper.updateByPrimaryKeySelective(teacherMessage);
            StudentMessage message = new StudentMessage();
            message.setApproval(true);
            message.setState(false);
            message.setSignTime(new Date());
            message.setStudentId(studentId);
            message.setTeacherId(teacherId);
            message.setTeacherState(true);
            int insert = studentMapper.insert(message);
            return i>0&&insert>0;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    @Override
    public Boolean refuseMessage(Integer messageId, String reason,String studentId,String teacherId) {
        if (messageId!=null&& StringUtils.isNotBlank(reason)){
            TeacherMessage teacherMessage = new TeacherMessage();
            teacherMessage.setId(messageId);
            teacherMessage.setState(true);
            int i = teacherMapper.updateByPrimaryKeySelective(teacherMessage);
            StudentMessage studentMessage = new StudentMessage();
            studentMessage.setState(false);
            studentMessage.setApproval(false);
            studentMessage.setSignTime(new Date());
            studentMessage.setReason(reason);
            studentMessage.setStudentId(studentId);
            studentMessage.setTeacherId(teacherId);
            studentMessage.setTeacherState(true);
            int insert = studentMapper.insert(studentMessage);
            return i>0&&insert>0;
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
    @Autowired
    teacherInfoMapper studentInfoMapper;
    @Override
    public Boolean updateTitle(teacherInfo teacherInfo) {
        if (teacherInfo!=null){
            log.info("传入学生信息对象：{}",teacherInfo);
            return studentInfoMapper.updateByPrimaryKeySelective(teacherInfo)>0;
        }
        log.info("传入空值");
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

}
