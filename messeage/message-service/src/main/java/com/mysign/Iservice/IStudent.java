package com.mysign.Iservice;

import com.mysign.bean.StudentMessage;
import com.mysign.bean.studentInfo;

import java.util.List;

public interface IStudent {
    List<StudentMessage> getStudentMessage(Integer id);

    Boolean addMessage(String studentId, String teacherId, String startdate, String startTime, String enddate, String endTime, String desc);

    Boolean updateTitle(studentInfo studentInfo);
}
