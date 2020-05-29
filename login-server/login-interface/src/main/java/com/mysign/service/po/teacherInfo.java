package com.mysign.service.po;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/9 11:07
 */
@Getter
@Setter
@Table(name = "tb_teacherInfo")
@NoArgsConstructor
public class teacherInfo {
    private Integer id;//教师表id
    private String teacherId;//教师工号
    private String teacherName;//教师姓名
    private String title;//头像
    private Date birthday;//出生日期
    private Boolean sex;//性别 教师性别,false女true男
    private String WorkingHours;//工作时长
    private String tel;//联系方式
    private  String GraduateSchool;//毕业院校
    private String educationBackground;//学历
    private String major;//主修方面
    @Transient
    private String studentSex;

    public String getStudentSex() {
        return this.sex?"男":"女";
    }


}