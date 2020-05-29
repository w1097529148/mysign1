package com.mysign.service.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;

/**
 * @Description 学生信息表
 * @Author Mr.Li
 * @Date 2020/2/9 10:56
 */
@Data
@Table(name = "tb_studentInfo")
@NoArgsConstructor
@AllArgsConstructor
public class studentInfo {
    private Integer id;//学生表id
    private String studentId;//学生学号
    private String student_name;//学生姓名
    private String title;//学生头像
    private Date birthday;//学生出生日期
    private Boolean sex;//学生性别  性别,false女true男
    private String address;//学生住址
    private String tel;//联系方式
    private String grade;//学生年级
    private String cla;//学生班级
    private String professional;//学生专业
    private String major;//主修方面
}
