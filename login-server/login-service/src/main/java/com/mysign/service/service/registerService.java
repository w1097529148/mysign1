package com.mysign.service.service;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.teacherInfoMapper;
import com.mysign.service.po.*;
import com.mysign.service.utils.MD5Utils;
import com.mysign.service.vo.ExceptionEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Locale;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/8 22:03
 */

@Service
public class registerService {
    @Autowired
    com.mysign.service.mapper.teacherLoginMapper teacherLoginMapper;
    @Autowired
    com.mysign.service.mapper.studentLoginMapper studentLoginMapper;

    @Autowired
    MD5Utils md5Utils;

    /**
     * @Description: 注册验证
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/9 9:38
     */
    public void registerRole1(String user, String code, String password, String password1, String role) {
        String Code = "code";
        code = "code";
        //判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成，等于!isBlank(String str)
        if (StringUtils.isNotBlank(password) & StringUtils.isNotBlank(password1) & StringUtils.isNotBlank(code) & StringUtils.isNotBlank(user) & StringUtils.isNotBlank(role)) {
            if (StringUtils.equals(Code, code)) {
                if (StringUtils.equals(password1, password)) {
                    password = md5Utils.getMD5(password);
                    int i = StringUtils.equals(role, "我是学生") ? studentLoginMapper.insert(new student(null, user, password)) : teacherLoginMapper.insert(new teacher(null, user, password));
                    if (!(i > 0))
                        throw new MySignException(ExceptionEnum.ROLE_ALREADY_AXISTS);
                } else {
                    throw new MySignException(ExceptionEnum.PASSWORD_IS_NOT_SAME);
                }
            }
            throw new MySignException(ExceptionEnum.CODE_IS_NOT_SAME);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }

    /**
     * @Description: 查询角色是否存在
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/11 9:51
     */
    public boolean selectRole(String user, String code, String password, String password1, String role) {
        String Code = "code";
        code = "code";
        //判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成，等于!isBlank(String str)
        if (StringUtils.isNotBlank(password) & StringUtils.isNotBlank(password1) & StringUtils.isNotBlank(code) & StringUtils.isNotBlank(user) & StringUtils.isNotBlank(role)) {
            if (StringUtils.equals(Code, code)) {
                if (StringUtils.equals(password1, password)) {
                    int i = StringUtils.equals(role, "我是学生") ? studentLoginMapper.selectCount(new student(null, user, null)) : teacherLoginMapper.selectCount(new teacher(null, user, null));
                    if (i > 0)
                        throw new MySignException(ExceptionEnum.ROLE_ALREADY_AXISTS);
                    else
                        return true;
                } else {
                    throw new MySignException(ExceptionEnum.PASSWORD_IS_NOT_SAME);
                }
            }
            throw new MySignException(ExceptionEnum.CODE_IS_NOT_SAME);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }


    /**
     * @Description: 添加学生
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/12 18:25
     */
    public Boolean addStudent(formValid formValid, student user, String code) {
        code = "code";
        studentInfo studentInfo = new studentInfo();
        if (StringUtils.equals("code", code)) {
            studentInfo.setStudentName(formValid.getName());
            studentInfo.setAddress(formValid.getAddress());
            try {
                studentInfo.setBirthday(DateUtils.parseDate(formValid.getBirthday().substring(0, 10), Locale.CHINA, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            studentInfo.setCla(formValid.getCla());
            studentInfo.setGrade(formValid.getGrade());
            studentInfo.setMajor(formValid.getMajor());
            studentInfo.setProfessional(formValid.getProfessional());
            studentInfo.setTel(formValid.getTel());
            studentInfo.setSex(StringUtils.equals("男", formValid.getGender()));
            studentInfo.setStudentId(user.getStudentId());
            studentInfo.setTitle("default");
            user.setPwd(new MD5Utils().getMD5(user.getPwd()));
            int insert = studentLoginMapper.insert(user);
            int i = studentLoginMapper.addStudentInfo(studentInfo);
            if (i > 0 && insert > 0) {
                return true;
            } else {
                throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
            }
        }
        return true;
    }


    /**
    *@Description:  注册教师角色
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/12 18:46
    */
    @Autowired
    teacherInfoMapper teacherInfoMapper;
    @Transactional
    public Boolean addTeacher(formValid formValid, student user, String code) {
        code = "code";
        teacherInfo teacherInfo = new teacherInfo();
        if (StringUtils.equals("code", code)) {
            teacher teacher = new teacher(null, user.getStudentId(), new MD5Utils().getMD5(user.getPwd()));
            try {
                teacherInfo.setBirthday(DateUtils.parseDate(formValid.getBirthday().substring(0, 10), Locale.CHINA, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            teacherInfo.setTeacherName(formValid.getName());
            teacherInfo.setGraduateSchool(formValid.getCla());
            teacherInfo.setMajor(formValid.getMajor());
            teacherInfo.setTel(formValid.getTel());
            teacherInfo.setTitle("default");
            teacherInfo.setSex(StringUtils.equals("男", formValid.getGender()));
            teacherInfo.setWorkingHours(formValid.getGrade());
            teacherInfo.setEducationBackground(formValid.getProfessional());

            int insert = teacherLoginMapper.insert(teacher);
            int i = teacherInfoMapper.insert(teacherInfo);
            if (i > 0 && insert > 0) {
                return true;
            } else {
                throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
            }
        }
        return true;
    }

}
