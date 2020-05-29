package com.mysign.mapper;

import com.mysign.bean.StudentMessage;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 9:38
 */
public interface studentMapper extends Mapper<StudentMessage> {
    @Insert("INSERT INTO teacher_message VALUES(null,#{studentId},#{teacherId},#{date},#{parse},#{endparse},0,#{desc})")
    Integer addMessage(String studentId, String teacherId, Date date, Date parse, Date endparse, String desc);

}
