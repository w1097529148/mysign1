package com.mysign.service.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

//学生登录表
@Table(name="tb_student")
@Data
@AllArgsConstructor
public class student {
    private Integer id;
    @Column(name = "studentId")
    private String studentId;
    private String pwd;
}
