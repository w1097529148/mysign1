package com.mysign.service.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

//教师登录表
@Table(name="tb_teacher")
@Data
@AllArgsConstructor
public class teacher {
    private Integer id;
    @Column(name = "teacherId")
    private String studentId;
    private String pwd;
}