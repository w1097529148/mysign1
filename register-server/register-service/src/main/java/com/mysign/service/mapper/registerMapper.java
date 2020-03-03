package com.mysign.service.mapper;

import com.mysign.service.po.student;
import com.mysign.service.po.studentInfo;
import com.mysign.service.po.teacher;
import com.mysign.service.po.teacherInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
public interface registerMapper extends Mapper<teacherInfo> {

    /**
    *@Description: 添加教师登录账号
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/13 15:32
    */
    @Insert("INSERT INTO tb_teacher(id,teacherId,pwd) VALUES(#{teacher.id},#{teacher.teacherId},#{teacher.pwd)")
    boolean addTeacher(@Param("teacher")teacher teacher);
/**
*@Description: 添加学生登录账号
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/13 15:32
*/
    @Insert("INSERT INTO tb_student(id,studentId,pwd) VALUES(#{student.id},#{student.studentId},#{student.pwd)")
    boolean addStudent(@Param("student") student student);


    /**
    *@Description: 添加学生基本信息
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/13 15:31
    */
@Insert("INSERT INTO tb_studentInfo VALUES(" +
        "#{studentInfo.id}," +
        "#{studentInfo.studentId}," +
        "#{studentInfo.student_name}," +
        "#{studentInfo.title},#{studentInfo.birthday}," +
        "#{studentInfo.sex}," +
        "#{studentInfo.address}," +
        "#{studentInfo.tel}," +
        "#{studentInfo.grade}," +
        "#{studentInfo.cla}," +
        "#{studentInfo.professional}," +
        "#{studentInfo.major})")
    int addStudentInfo(@Param("studentInfo")studentInfo studentInfo);

/**
*@Description: 查询学生是否存在
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/13 15:31
*/
@Select("select count(*) from tb_student where studentId=#{studentId}")
int selectStudent(@Param("studentId") String studentId);

/**
*@Description: 查询教师是否存在
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/13 15:31
*/
    @Select("select count(*) from tb_teacher where teacherId=#{teacherId}")
    int selectTeacher(@Param("teacherId") String teacherId);
}
