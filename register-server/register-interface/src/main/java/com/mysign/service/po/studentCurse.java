package com.mysign.service.po;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/9 17:06
 */
@Data
@Table(name="tb_student_curse")
public class studentCurse {
    private Integer id;
    private String studentId;
    private int curseId;
}
