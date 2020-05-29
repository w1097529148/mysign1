package com.mysign.sign;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
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
    private Integer id;//教师签到表id
    @Column(name = "teacher_id")
    private String teacherId;//教师编号
    @Column(name = "curse_id")
    private Integer curseId;//课程id
    @Column(name = "sign_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date signTime;//发布时间
    @Column(name="sign_coordinates_x")
    private Float signCoordinatesX;//签到x坐标
    @Column(name="sign_coordinates_y")
    private Float signCoordinatesY;//签到Y坐标
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

}
