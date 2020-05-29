package com.mysign.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/3/29 8:58
 */
@Table(name = "student_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StudentMessage {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;//主键id
    private String teacherId;//教师id
    private String studentId;//学生id
    private java.util.Date signTime;//时间
    private Boolean state;//是否为已读
    private Boolean approval;//是否批准
    private String reason;//理由
    private Boolean teacherState;
    @Transient
    private String isApproval;
    @Transient
    private String date;
    @Transient
    private String messageState;

    public String getIsApproval() {
        if (this.teacherState)
        return this.approval?"批准":"驳回";
        else
            return "待批复";
    }

    public String getMessageState() {
        return this.state?"已读":"未读";
    }
    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.signTime);
    }

    public StudentMessage(Integer id, String teacherId, String studentId, Date signTime, Boolean state, Boolean approval, String reason) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.signTime = signTime;
        this.state = state;
        this.approval = approval;
        this.reason = reason;
    }

}
