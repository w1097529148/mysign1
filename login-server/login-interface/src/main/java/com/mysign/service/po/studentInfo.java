package com.mysign.service.po;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 学生信息表
 * @Author Mr.Li
 * @Date 2020/2/9 10:56
 */
@Data
@Table(name = "tb_studentInfo")
@NoArgsConstructor
public class studentInfo implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;//学生表id
    @Column(name = "student_id")
    private String studentId;//学生学号
    private String studentName;//学生姓名
    private String title;//学生头像
    private Date birthday;//学生出生日期
    private Boolean sex;//学生性别  性别,false女true男
    private String address;//学生住址
    private String tel;//联系方式
    private String grade;//学生年级
    private String cla;//学生班级
    private String professional;//学生专业
    private String major;//主修方面

    @Ignore
    @Transient
    private String studentSex;
@Ignore
    public String getStudentSex() {
        return this.sex?"男":"女";
    }
}
