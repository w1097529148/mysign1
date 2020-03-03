package com.mysign.mapper;

import com.mysign.sign.signTeacher;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

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
}
