package com.mysign.service.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/9 11:07
 */
@Data
@Table(name = "tb_studentInfo")
@NoArgsConstructor
@AllArgsConstructor
public class teacherInfo {
    private Integer id;//教师表id
    private String teacherId;//教师工号
    private String teacher_name;//教师姓名
    private String title;//头像
    private Date birthday;//出生日期
    private Boolean sex;//性别 教师性别,false女true男
    private String Working_hours;//工作时长
    private String tel;//联系方式
    private  Character Graduate_school;//毕业院校
    private String education_background;//学历
    private String major;//主修方面
}