package com.mysign.service.mapper;


import com.mysign.service.po.studentInfo;
import com.mysign.service.po.teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import com.mysign.service.po.student;
public interface studentLoginMapper extends Mapper<student> {

    @Select("SELECT * FROM tb_studentInfo WHERE student_id=#{studentId}")
    studentInfo selectByStudentId(String studentId);

    @Insert("INSERT INTO tb_teacher(id,teacher_id,pwd) VALUES(#{teacher.id},#{teacher.teacherId},#{teacher.pwd)")
    boolean addTeacher(@Param("teacher") teacher teacher);

    @Insert("INSERT INTO tb_student(id,student_id,pwd) VALUES(#{student.id},#{student.studentId},#{student.pwd)")
    boolean addStudent(@Param("student") student student);

    @Select("select count(*) from tb_student where student_id=#{studentId}")
    int selectStudent(@Param("studentId") String studentId);

    @Select("select count(*) from tb_teacher where teacher_id=#{teacherId}")
    int selectTeacher(@Param("teacherId") String teacherId);


    /**
    *@Description:  根据学生登录学号查询学生信息表，学生课程表
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/13 10:27
    */
    @Select("SELECT a.*,c.* FROM tb_studentInfo a  JOIN tb_student_curse b ON a.`student_id`=b.`student_id` AND a.`student_id`=#{studentId}  JOIN tb_curse c ON b.`curse_id`=c.`id`")
    List getStudentAllMessage(String studentId);
}
