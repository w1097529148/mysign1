package com.mysign.service.service;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.curseReceiver;
import com.mysign.service.mapper.teacherLoginMapper;
import com.mysign.service.po.curse;
import com.mysign.service.po.student;
import com.mysign.service.po.teacher;
import com.mysign.service.po.teacherInfo;
import com.mysign.service.utils.MD5Utils;
import com.mysign.service.vo.ExceptionEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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
        System.out.println(studentId);
        System.out.println(teacherId);
        Map<Object, Object> userCurse = curseReceiver.getStudentCurse(studentId,teacherId);
        return userCurse;
    }
}
