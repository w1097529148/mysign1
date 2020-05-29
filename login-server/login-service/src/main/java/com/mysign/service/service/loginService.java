package com.mysign.service.service;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.curseReceiver;
import com.mysign.service.mapper.studentInfoLoginMapper;
import com.mysign.service.mapper.teacherInfoMapper;
import com.mysign.service.mapper.teacherLoginMapper;
import com.mysign.service.po.*;
import com.mysign.service.utils.MD5Utils;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class loginService {
    @Autowired
    com.mysign.service.mapper.studentLoginMapper studentLoginMapper;
    @Autowired
    teacherLoginMapper teacherMapper;
    @Autowired
    MD5Utils md5Utils;
    @Autowired
    curseReceiver curseReceiver;
    /**
     * @Description: 登录验证  返回map集合
     * @Params [studentId, pwd, value]
     * @Return java.lang.Object
     * @Author Mr.Li
     * @Date 2020/2/8 20:37
     */

    @Autowired
    curseReceiver curseProvider;

    @Autowired
    studentInfoLoginMapper studentInfoMapper;
    public Map<Object, Object> getMainPage(String studentId, String pwd, String value) {
        Map<Object, Object> userCurse = new HashMap<>();
        if (StringUtils.equals(value, "我是学生")) {
            student s = studentLoginMapper.selectOne(new student(null, studentId, null));
            if (ObjectUtils.isNotEmpty(s)) {
                if (StringUtils.equals(md5Utils.getMD5(pwd), s.getPwd())){
                    userCurse.put("学生基本信息",studentLoginMapper.selectByStudentId(s.getStudentId()));
                    return userCurse;
                }

            }else{
                throw new MySignException(ExceptionEnum.PASSWORD_IS_WRONG);
            }
        }
        if (StringUtils.equals(value, "我是老师")) {
            teacher s = teacherMapper.selectOne(new teacher(null, studentId, null));
            if (ObjectUtils.isNotEmpty(s)) {
                if (StringUtils.equals(s.getPwd(), md5Utils.getMD5(pwd))){
                    userCurse.put("教师基本信息",teacherMapper.selectByTeacherId(s.getStudentId()));
                    return userCurse;
                }

            }
            throw new MySignException(ExceptionEnum.PASSWORD_IS_WRONG);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    /**
    *@Description: 根据教师id获取教师详细信息
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/14 10:48
    */
    public teacherInfo getTeacherMessage(String teacherId){
        if(StringUtils.isNotBlank(teacherId))
        return teacherMapper.selectByTeacherId(teacherId);
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);

    }

    public Map<Object,Object> getMessages(String studentId,String teacherId) {
        Map<Object, Object> userCurse = curseReceiver.getStudentCurse(studentId,teacherId);
        log.info("返回的结果为=：{}",userCurse);
        return userCurse;
    }
@Autowired
    teacherInfoMapper teacherInfoMapper;
    public Boolean selectUserByPhoneAndRadio(String phone, String radio) {
        if ("学生".equals(radio)){
            studentInfo studentInfo=new studentInfo();
            studentInfo.setTel(phone);
            return (studentInfoMapper.selectCount(studentInfo)>0);
        }else if ("老师".equals(radio)){
            teacherInfo teacherInfo=new teacherInfo();
            teacherInfo.setTel(phone);
            return (teacherInfoMapper.selectCount(teacherInfo)>0);
        }
        throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
    }

    public Boolean putPassword(String password,String Role,String phone) {
        if (!StringUtils.isAllBlank(password,Role))
        if ("学生".equals(Role)){
            studentInfo studentInfo=new studentInfo();
            studentInfo.setTel(phone);
            com.mysign.service.po.studentInfo studentInfo1 = studentInfoMapper.selectOne(studentInfo);
            if (studentInfo1!=null){
                String md5 = new MD5Utils().getMD5(password);
                student student = new student(null,null,md5);
                Example example = new Example(studentInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("studentId",studentInfo.getStudentId());
                return studentLoginMapper.updateByExampleSelective(student,example)>0;
            }
        }
        if ("老师".equals(Role)){
            teacherInfo teacherInfo=new teacherInfo();
            teacherInfo.setTel(phone);
            com.mysign.service.po.teacherInfo teacherInfo1 = teacherInfoMapper.selectOne(teacherInfo);
            if (teacherInfo1!=null){
                String md5 = new MD5Utils().getMD5(password);
                teacher student = new teacher(null,null,md5);
                Example example = new Example(teacherInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("teacherId",teacherInfo1.getTeacherId());
                return teacherMapper.updateByExampleSelective(student,example)>0;
            }
        }
       throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
}
