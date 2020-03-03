package com.mysign.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/28 8:49
 */
@Table(name = "teacher_sign_table")
@Data
@NoArgsConstructor
public class signTeacher implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private int id;//教师签到表id
    @Column(name = "teacher_id")
    private String teacherId;//教师编号
    @Column(name = "curse_id")
    private int curseId;//课程id
    @Column(name = "sign_time")
    private Date signTime;//发布时间
    @Column(name="sign_coordinates_x")
    private Double signCoordinatesX;//签到x坐标
    @Column(name="sign_coordinates_y")
    private Double signCoordinatesY;//签到Y坐标
    @Transient
    private String Time;//修改显示法布施时间样式
    @Transient
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String getTime() {
        return sdf.format(this.signTime);
    }

    public signTeacher( String teacherId, int curseId, Date signTime, Double signCoordinatesX, Double signCoordinatesY) {
        this.teacherId = teacherId;
        this.curseId = curseId;
        this.signTime = signTime;
        this.signCoordinatesX = signCoordinatesX;
        this.signCoordinatesY = signCoordinatesY;
    }
}
