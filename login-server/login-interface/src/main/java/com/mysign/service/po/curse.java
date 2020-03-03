package com.mysign.service.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="tb_curse")
@Data
@NoArgsConstructor
public class curse {
    @Id
    @KeySql(useGeneratedKeys = true)
    private int id; //'课程表id'
    private String curseName; //'课程名称'
    private String title; //课程图片
    private String teacherId; //任课教师
    private Integer score; //课程学分
    private String place; //上课地点
    private String times; //上课时间坐标形式：如周一第一节(1,1),周一三四节(1,3,4),周一三四节，周二七八节(1,3,4).(2,7,8)
    private Boolean whether;//是否是必修课 1必修 0选修
    private Boolean curseWeek;//单双周，0双1单
    private String duration;//课程时长
    private String startTime;//起始周期
    private String endTime;//结束周期
    private String other;//其他字段
    @Transient
private  String whethe;
    @Transient
private String week;
    public Integer getScore() {
        return this.score/10;
    }


    public String getWhethe() {
        return this.whether?"必修":"选修";
    }

    public String getWeek() {
        return this.curseWeek?"单周":"双周";
    }


}