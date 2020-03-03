package com.mysign.sign;

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
import java.text.SimpleDateFormat;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/25 18:09
 */
@Table(name = "student_sign_table")
@Data
@NoArgsConstructor
public class signStudent implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private int id;//学生表id
    private String studentId;//学生学号
    @Column(name = "teacher_curse_id")
    private int curseId;//课程id
    @Column(name = "sign_time")
    private Date signTime;//签到时间
    @Column(name="sign_state")
    private Boolean signState;//签到状态
    @Column(name="sign_coordinates_x")
    private Double signCoordinatesX;//签到x坐标
    @Column(name="sign_coordinates_y")
    private Double signCoordinatesY;//签到Y坐标
    @Transient
    private String State;
    @Transient
    private String Time;
    @Transient
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
public String getState(){
    return this.signState?"已签到":"未签到";

}
    public String getTime() {
        return sdf.format(this.signTime);
    }

    public signStudent(String studentId, int curseId, Date signTime, Boolean signState, Double signCoordinatesX, Double signCoordinatesY) {
        this.studentId = studentId;
        this.curseId = curseId;
        this.signTime = signTime;
        this.signState = signState;
        this.signCoordinatesX = signCoordinatesX;
        this.signCoordinatesY = signCoordinatesY;
    }
}
