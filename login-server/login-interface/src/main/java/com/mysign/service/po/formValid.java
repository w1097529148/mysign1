package com.mysign.service.po;

import lombok.Data;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/11 23:13
 */

@Data
public class formValid  {
    String name;
    String address;
    String gender;
    String birthday;
    String tel;
    String cla;//毕业院校 班级
    String major;
    String professional;//学历 专业
    String grade; //工作时长 年级
}
