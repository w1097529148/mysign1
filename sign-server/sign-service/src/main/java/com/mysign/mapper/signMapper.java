package com.mysign.mapper;

import com.mysign.sign.signStudent;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:23
 */
public interface signMapper extends Mapper<signStudent> {
    @Select("SELECT a.`sign_state`,a.`sign_time`,c.`curse_name`,b.`start_time`,b.`end_time`,c.`place` FROM student_sign_table a JOIN teacher_sign_table b ON a.`sign_table_id`=b.`id` AND a.`student_id`=#{studentId} JOIN tb_curse c ON c.`id`=b.`curse_id`")
    List<Map<String,Object>> getSignMessage(String studentId);
}
