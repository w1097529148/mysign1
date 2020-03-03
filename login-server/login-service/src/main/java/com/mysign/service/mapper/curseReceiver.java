package com.mysign.service.mapper;

import com.mysign.service.po.curse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Component
@FeignClient("CURSE-SERVER")
public interface curseReceiver {

    /**
     *@Description: 查询全部课程 前端的与我相关 可以根据字段匹配
     *@Params
     *@Return
     *@Author Mr.Li
     *@Date 2020/2/9 16:58
     */
    @GetMapping("/curse/getAllCurse")
    public List<curse> getAllCurse();

    /**
     *@Description: 获取学生课表，以及学生的课程基本信息
     *@Params
     *@Return
     *@Author Mr.Li
     *@Date 2020/2/9 22:28
     */
    @GetMapping("/curse/student")
    public Map<Object, Object> getStudentCurse( @RequestParam(value = "studentId",required = false) String studentId,
                                                @RequestParam(value = "teacherId",required = false) String teacherId
    );

    /**
     *@Description: 获取教师课表以及教师的课程基本信息
     *@Params
     *@Return
     *@Author Mr.Li
     *@Date 2020/2/9 22:29
     */
    @GetMapping("/curse/teacher")
    public Map<Object, Object> getTeacherCurse(@RequestParam("teacherId")String teacherId);
}
