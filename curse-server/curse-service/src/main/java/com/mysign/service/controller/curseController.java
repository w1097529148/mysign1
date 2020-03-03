package com.mysign.service.controller;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.po.curse;
import com.mysign.service.po.curseTimes;
import com.mysign.service.po.teacherInfo;
import com.mysign.service.vo.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/curse")
public class curseController {
    @Autowired
    com.mysign.service.service.curseService curseService;


//查看课程表，以及所有课程信息
    @GetMapping("/getAllCurse")
    public ResponseEntity<List<curse>> getAllCurse(){
        return ResponseEntity.ok(curseService.getAllCurse());
    }
/**
*@Description: 学生课程k+解析学生课表v
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/10 15:26
*/
    @GetMapping("student")
    public  ResponseEntity<Map<Object,Object>> getStudentCurse( @RequestParam(value = "studentId",required = false) String studentId,
                                                                @RequestParam(value = "teacherId",required = false) String teacherId){
        System.out.println(studentId);
        System.out.println(teacherId);
        return ResponseEntity.ok(curseService.getStudentCurse(studentId,teacherId));
    }

//    /**
//    *@Description: 教师课程k+解析教师课表v
//    *@Params
//    *@Return
//    *@Author Mr.Li
//    *@Date 2020/2/10 15:27
//    */
//    @GetMapping("teacher")
//    public  ResponseEntity<Map<Object,Object>> getTeacherCurse(@RequestParam("teacherId") String teacherId){
//        return ResponseEntity.ok(curseService.getStudentCurse(teacherId));
//    }

   @GetMapping("getTeacher")
   public ResponseEntity<teacherInfo> getTeacher(
           @RequestParam("curseId")Integer curseId
   ){
        if (curseId!=null)
        return ResponseEntity.ok(curseService.getTeacher(curseId));
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);

   }

    @GetMapping("/getStudentCurseTable")
    public ResponseEntity<Map<Object,Object>> getStudentCurseTable(
            @RequestParam("Id") String Id
    ){
        return ResponseEntity.ok(curseService.getPersonalCurse(Id));
    }

    @PostMapping("studentAddCurse")
    public ResponseEntity<Boolean> StudentAddCurse(
            @RequestParam("CurseId") Integer CurseId,
            @RequestParam("studentId") String studentId

    ){
        return ResponseEntity.ok(curseService.studentAddCurse(CurseId,studentId));

    }

    @GetMapping("getCurseTimes")
    public ResponseEntity<Map> getCurseTimeAndCurse(
            @RequestParam("curseTimes") String curseTimes,
            @RequestParam("CurseId") Integer CurseId
    ){
        return ResponseEntity.ok(curseService.getCurseTimeAndCurse(curseTimes,CurseId));
    }


    @GetMapping("getCurseByName")
    public ResponseEntity<List<curse>> getCurseByName(

            @RequestParam("CurseName")String  CurseName
    ){

        return ResponseEntity.ok(curseService.getCurseByName(CurseName));
    }



    @GetMapping("getCurse")
    public ResponseEntity<Map<Object,Object>> getCurse(
            @RequestParam(value = "teacherId",required = false)String teacherId,
            @RequestParam(value = "studentId",required = false) String studentId){

        return ResponseEntity.ok(curseService.getCurse(studentId,teacherId));
    }

    @GetMapping("getTeacherCurse")
    public ResponseEntity<Map> getTeacherCurse(
            @RequestParam("Id")String Id
    ){
        return ResponseEntity.ok(curseService.getTeacherCurse(Id));
    }

}
