package com.mysign.bean;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 8:58
 */
@NoArgsConstructor
@Data
@Table(name = "teacher_message")
@AllArgsConstructor
@Component
public class TeacherMessage {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;//教师信息表id
    private String teacherId;//教师id
    private String studentId;//学生id
    private Date signTime;//发布时间
    @Column(name = "start_time")
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private Boolean state;//消息状态true 已读 false未读
    private String des;//描述
    @Transient
    private String start;
    @Transient
    private String end;
    @Transient
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transient
    private String signState;//消息状态

    public String getStart() {
        if (this.startTime!=null)
        return simpleDateFormat.format(this.startTime);
        return null;
    }

    public String getEnd() {
        return this.endTime!=null?simpleDateFormat.format(this.endTime):null;
    }

    public String getSignState() {
        return this.state?"已读":"未读";
    }

    public Date getSignTime() {
        if (this.signTime!=null)
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(this.signTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Date getStartTime() {
        if (this.startTime!=null)
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(this.startTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Date getEndTime() {
        if (this.endTime!=null)
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(this.endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public TeacherMessage(Integer id, String teacherId, String studentId, Date signTime, Date startTime, Date endTime, Boolean state, String des) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.signTime = signTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.des = des;
    }

    public void setSignTime(Date signTime) {
        try {
            this.signTime = simpleDateFormat.parse(simpleDateFormat.format(signTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setStartTime(Date startTime) {
        try {
            this.startTime = simpleDateFormat.parse(simpleDateFormat.format(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setEndTime(Date endTime) {
        try {
            this.endTime = simpleDateFormat.parse(simpleDateFormat.format(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public String getDate() {
        return simpleDateFormat.format(this.signTime);
    }

}
