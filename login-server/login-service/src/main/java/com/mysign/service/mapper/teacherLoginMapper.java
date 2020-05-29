package com.mysign.service.mapper;

import com.mysign.service.po.teacher;
import com.mysign.service.po.teacherInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface teacherLoginMapper  extends Mapper<teacher> {

    @Select("SELECT * FROM tb_teacherInfo WHERE teacher_id=#{teacherId}")
    teacherInfo selectByTeacherId(String teacherId);

    /**
     *@Description: 当查询全部课程时返回的老师集合
     *@Params
     *@Return
     *@Author Mr.Li
     *@Date 2020/2/16 10:49
     */
    @Select("SELECT a.* FROM tb_teacherInfo a JOIN tb_curse b ON a.teacher_id=b.teacher_id ORDER BY b.`id` DESC")
    List<teacherInfo> getAllTeachersByCurse();

    /**
    *@Description: 通过学生学号查询全部教师(与我相关)
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/16 10:58
    */
    @Select("SELECT a.* FROM tb_teacherInfo a JOIN tb_curse b ON a.teacher_id=b.teacher_id JOIN tb_student_curse c ON b.`id`=c.`curse_id` AND c.`student_id`=#{studentId}")
    List<teacherInfo> getAllTeachersByStudent(String studentId);
}
