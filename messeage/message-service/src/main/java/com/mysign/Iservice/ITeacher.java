package com.mysign.Iservice;

import com.mysign.bean.TeacherMessage;
import com.mysign.bean.studentInfo;
import com.mysign.bean.teacherInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITeacher {
    List<TeacherMessage> getStudentMessage(Integer id);
    Boolean agreeMessage(Integer messageId, String studentId, String teacherId);

    Boolean refuseMessage(Integer messageId, String reason,String studentId,String teacherId);
    Boolean updateTitle(teacherInfo teacherInfo);
}
