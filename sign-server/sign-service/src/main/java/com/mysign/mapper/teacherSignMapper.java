package com.mysign.mapper;

import com.mysign.sign.signTeacher;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface teacherSignMapper  extends Mapper<signTeacher> {
    /**
    *@Description: 根据课程id和当前日期查询要签到的课程
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/29 9:42
    */
    @Select("SELECT * FROM teacher_sign_table WHERE curse_id=#{curseId} AND sign_time LIKE #{parse} ORDER BY id DESC LIMIT 1")
    signTeacher selectByCurseIdAndTime(Integer curseId, String parse);
    @Select("SELECT a.`sign_state` signState,a.`sign_time` SignTime," +
            "b.`curse_id` curseId,b.`state`,b.`start_time` startTime,b.`end_time` endTime,b.`sign_time` pushTime," +
            "c.`student_name` studentName,d.`curse_name` curseName,d.`place` " +
            "FROM student_sign_table a JOIN tb_studentInfo c ON a.`student_id`=c.`student_id` " +
            "JOIN teacher_sign_table b ON a.`sign_table_id` =b.`id` " +
            "JOIN tb_curse d ON d.`id`=b.`curse_id` AND b.`teacher_id`=#{teacherId}")
    List<Map<String,Object>> getStudentSignMessageByTeacherId(String teacherId);
    @Select("<script> SELECT a.`curse_name` curseName,a.`place`,b.`sign_time` pushTime,b.`start_time` startTime,b.`end_time` endTime," +
            "c.`student_name` studentName,e.`sign_time` signTime,e.`sign_state` signState " +
            "FROM tb_curse a JOIN teacher_sign_table b ON a.`id`=b.`curse_id`" +
            "<if test='curseName!=null'> AND a.`curse_name`=#{curesName}</if> " +
            "JOIN tb_student_curse d ON d.`curse_id`=a.`id` JOIN tb_studentInfo c ON c.`student_id`=d.`student_id`" +
            "<if test='studentName!=null'> AND c.`student_name`=#{studentName}</if> " +
            "JOIN student_sign_table e ON b.`id`=e.`sign_table_id`" +
            "</script>"
    )
    List<Map<String,Object>> findStudentSignMessage(String curseName,String studentName);
}
