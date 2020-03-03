package com.mysign.service.mapper;

import com.mysign.service.po.curse;
import com.mysign.service.po.teacherInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface curseMapper extends Mapper<curse> {
/**
*@Description: 两表联查根据学生学号查询其课程
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/12 21:11
*/
    @Select("SELECT a.* FROM tb_curse a JOIN tb_student_curse b ON a.`id`=b.`curse_id` AND b.student_id=#{studentId}")
    List<curse> getStudentCurses(String studentId);



    /**
    *@Description: 查询老师全部课程
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/9 20:40
    */
    @Select("SELECT * FROM tb_curse WHERE teacher_id = #{teacherId}")
    List<curse> getTeacherCurses(String teacherId);


    /**
    *@Description: 多表联查 根据学生登录账号查询学生课程，学生基本信息（未使用）
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/13 10:52
    */
    @Select("SELECT a.*,c.* FROM tb_studentinfo a  JOIN tb_student_curse b ON a.`student_id`=b.`student_id` AND a.`student_id`=#{studentId}  JOIN tb_curse c ON b.`curse_id`=c.`id`")
    List getStudentAllMessage(String studentId);

    /**
    *@Description: 查询全部课程
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/13 16:29
    */
    @Select("SELECT * FROM tb_curse ORDER BY id DESC")
    List<curse> selectAll();

    @Select("SELECT * FROM tb_curse a JOIN tb_teacherInfo b ON a.`teacher_id`=b.`teacher_id`")
    List getTeacherAndCurse();

@Select("SELECT * FROM tb_teacherinfo WHERE teacher_id IN(SELECT teacher_id FROM tb_curse WHERE id=#{curseId})")
    teacherInfo getTeacherByCurseId(Integer curseId);

@Insert("INSERT INTO tb_student_curse VALUES(NULL,#{studentId},#{curseId})")
int studentAddCurse(Integer curseId,String studentId);

@Select("SELECT COUNT(*) FROM tb_student_curse WHERE curse_id=#{curseId}")
int whetherChoose(Integer curseId);
    /**
     *@Description: 当查询全部课程时返回的老师集合
     *@Params
     *@Return
     *@Author Mr.Li
     *@Date 2020/2/16 10:49
     */
    @Select("SELECT a.* FROM tb_teacherinfo a JOIN tb_curse b ON a.teacher_id=b.teacher_id ORDER BY b.`id` DESC")
    List<teacherInfo> getAllTeachersByCurse();

    /**
     *@Description: 通过学生学号查询全部教师(与我相关)
     *@Params
     *@Return
     *@Author Mr.Li
     *@Date 2020/2/16 10:58
     */
    @Select("SELECT a.* FROM tb_teacherinfo a JOIN tb_curse b ON a.teacher_id=b.teacher_id JOIN tb_student_curse c ON b.`id`=c.`curse_id` AND c.`student_id`=#{studentId}")
    List<teacherInfo> getAllTeachersByStudent(String studentId);
@Select("SELECT * FROM tb_curse WHERE curse_name LIKE  #{curseName}")
    List<curse> getCurseByName(String curseName);
}
