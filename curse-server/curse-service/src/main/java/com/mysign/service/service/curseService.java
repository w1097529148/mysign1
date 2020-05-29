package com.mysign.service.service;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.curseMapper;
import com.mysign.service.po.curse;
import com.mysign.service.po.curseTimes;
import com.mysign.service.po.datetime;
import com.mysign.service.po.teacherInfo;
import com.mysign.service.vo.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class curseService {

    @Autowired
    curseMapper curseMapper1;

    /**
     * @Description: 根据课程名称模糊查询全部课程
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/19 22:33
     */
    @Transactional
    public Map<String,Object> getCurseByName(String CurseName) {

        if (StringUtils.isBlank(CurseName))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        Map<String,Object> map=new ConcurrentHashMap<>();
        List<curse> curseByName = curseMapper1.getCurseByName("%" + CurseName + "%");
                map.put("curse",curseByName);
            List<teacherInfo> teacherByCurseName = curseMapper1.findTeacherByCurseName("%" + CurseName + "%");
            if (teacherByCurseName!=null){
                map.put("teacher",teacherByCurseName);
                return map;
            }
            throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
    }

    /**
     * @Description: 解析选定的课程
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/9 17:01
     */
    public Map<Object, Object> getCurseTable(List<curse> lists) {
        String str = "";
        int k = 0;
        int index = 0;
        List<curseTimes> lis = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();
        Object str1[] = new Object[12];
        for (int i = 0; i < 12; i++) {
            curseTimes curseTimes1 = new curseTimes("", "", "", "", "", "", "");
            for (curse list : lists) {
                str = list.getTimes();
                log.debug("获取到的课表时间：{}",str);
                String[] s = StringUtils.split(str, ",");
                for (String s1 : s) {
                    s1 = StringUtils.substring(s1, 1, -1);
                    String[] s2 = StringUtils.split(s1, ".");
                    k = Integer.parseInt(s2[0]);
                    for (int j = 1; j < s2.length; j++) {
                        index = Integer.parseInt(s2[j])-1;
                        if (index == i) {
                            String s3 = list.getCurseName() +"<br/>"+ list.getScore() + "分<br/>" + list.getWhethe() +"<br/>"+ list.getWeek()+"<br/>"+ list.getStartTime() + "~" + list.getEndTime() +"<br/>"+ list.getPlace();
                            curseTimes1 = getObject(curseTimes1, k, s3);
                        }
                    }
                }
            }
            lis.add(curseTimes1);
        }
        map.put("个人课表", lis);
        map.put("全部信息", lists);
        return map;
    }

    /**
    *@Description: 获取学生个人课程
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/25 10:32
    */
    public Map<Object, Object> getPersonalCurse(String StudentId) {
        List<curse> studentCurses = curseMapper1.getStudentCurses(StudentId);
        if (CollectionUtils.isEmpty(studentCurses))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);

        Map<Object, Object> curseTable = getCurseTable(studentCurses);
        if (CollectionUtils.isEmpty(curseTable))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        return curseTable;
    }

    /**
     * @Description: 二维数组获取课程表
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/15 10:50
     */
//    public   Map<Object,Object>  getCurseTable1(List<curse> lists){
//        Map<Object,Object> map=new HashMap<>();
//        String[][] strings=new String[13][7];
//        for (curse list:lists) {
//            String[] s = StringUtils.split(list.getTimes(), ".");
//            list.setTimes(getCurseTime(list.getTimes()));
//            for (String s1 : s) {
//                char[] chars=s1.toCharArray();
//                for (int i = 3; i < chars.length; i=i+2) {
//                    strings[Integer.parseInt(String.valueOf(chars[i]))][Integer.parseInt(String.valueOf(chars[1]))]=list.getCurseName()+"\n学分:" + list.getScore()+"分\n"+list.getWhethe()+"\n" +list.getWeek()+"\n" + list.getStartTime() + "~" + list.getEndTime() + "\n" + list.getPlace() + "\n" + list.getTeacherId();
//                }
//            }
//        }
//map.put("个人课表",strings);
//map.put("全部信息",lists);
//return map;
//    }


//
//
//
//    public   String[][]  getCurseTable2(String studentId){
//         List<curse> lists = curseMapper1.getStudentCurses(studentId);
//         if (CollectionUtils.isEmpty(lists))
//             throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
//        String[][] strings=new String[13][7];
//        for (curse list:lists) {
//            String[] s = StringUtils.split(list.getTimes(), ".");
//            list.setTimes(getCurseTime(list.getTimes()));
//            for (String s1 : s) {
//                char[] chars=s1.toCharArray();
//                for (int i = 3; i < chars.length; i=i+2) {
//                    strings[Integer.parseInt(String.valueOf(chars[i]))][Integer.parseInt(String.valueOf(chars[1]))]=list.getCurseName()+"\n学分:" + list.getScore()+"分\n"+list.getWhethe()+"\n" +list.getWeek()+"\n" + list.getStartTime() + "~" + list.getEndTime() + "\n" + list.getPlace() + "\n" + list.getTeacherId();
//                }
//            }
//        }
//        return strings;
//    }
    private curseTimes getObject(curseTimes object, int k, String s2) {
        if (k == 1)
            object.setMonday(s2);
        if (k == 2)
            object.setTuesday(s2);
        if (k == 3)
            object.setWednesday(s2);
        if (k == 4)
            object.setThursday(s2);
        if (k == 5)
            object.setFriday(s2);
        if (k == 6)
            object.setSaturday(s2);
        if (k == 7)
            object.setSunday(s2);
        return object;

    }

    /**
     * @Description: 查询全部课程 前端的与我相关 可以根据字段匹配
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/9 16:58
     */
    public List<curse> getAllCurse() {
        List<curse> lists = curseMapper1.selectAll();
        return lists;
    }


    /**
     * @Description: 解析课程 将课程时间解析成字符串
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/15 0:43
     */
    public Map<Object,Object> getCurseTimeAndCurse(String curseTime, Integer CurseId,String studentId) {
        if (StringUtils.isAllBlank(curseTime,studentId) && CurseId == null)
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        Map map = new HashMap();
        Boolean b = true;
        int i1 = curseMapper1.whetherChoose(CurseId,studentId);
        if (i1 == 0) {
            b = false;
        }
        String[] s = StringUtils.split(curseTime, ",");
        String week = "";
        for (String s1 : s) {
            s1 = StringUtils.substring(s1, 1, -1);
            String[] s2 = StringUtils.split(s1, ".");
            week = week + getWeek(s2[0]);
            for (int i = 1; i < s2.length; i++) {
                week = week + "第" + s2[i] + "节";
            }
            week = week + ",";
        }
        map.put(StringUtils.substring(week, 0, -1), b);
        return map;
    }


    /**
     * @Description: 生成星期数组
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/15 0:44
     */
    public String getWeek(String week) {
        int w = Integer.parseInt(week);
        String[] strings = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return strings[w - 1];
    }


    /**
     * @Description: 获取学生课表，以及学生的课程基本信息
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/9 22:28
     */
    public Map<Object, Object> getStudentCurse(String studentId, String teacherId) {
        if (StringUtils.isBlank(studentId)&&StringUtils.isBlank(teacherId))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        log.info("获取课程列表开始=============");
        List<teacherInfo> allTeachersByStudent = null;
        List<curse> CurseWithMe = null;//与我相关
        if (StringUtils.isNotBlank(studentId)) {
            allTeachersByStudent = curseMapper1.getAllTeachersByStudent(studentId);
            log.info("根据学生学号:{} 获取老师的集合：{}",allTeachersByStudent);
            CurseWithMe = curseMapper1.getStudentCurses(studentId);
            log.info("根据学生学号：{}获取到的学生课程集合：{}",studentId,CurseWithMe);
        }
        if (StringUtils.isNotBlank(teacherId))
            CurseWithMe = curseMapper1.getTeacherCurses(teacherId);
        if (!CollectionUtils.isEmpty(CurseWithMe)) {
            Map<Object, Object> curseTable = new HashMap<>();
            if (!CollectionUtils.isEmpty(allTeachersByStudent))
                curseTable.put("我的教师", allTeachersByStudent);
            curseTable.put("与我相关", CurseWithMe);
            log.info("返回的map集合为：{}",curseTable);
            return curseTable;
        }
        else{
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        }

    }

    /**
     * @Description: 获取教师课表以及教师的课程基本信息
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/9 22:29
     */
    public Map<Object, Object> getTeacherCurse(String teacherId) {
        List<curse> teacherCurses = curseMapper1.getTeacherCurses(teacherId);
        Map<Object, Object> curseTable = getCurseTable(teacherCurses);
        return curseTable;

    }

    /**
     * @Description: 根据课程id获取教师信息
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/18 22:17
     */
    public teacherInfo getTeacher(Integer curseId) {
        teacherInfo teacherByCurseId = curseMapper1.getTeacherByCurseId(curseId);
        if (ObjectUtils.isEmpty(teacherByCurseId))
            throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
        return teacherByCurseId;
    }

    /**
     * @Description: 学生根据课程id选择课程
     * @Params
     * @Return
     * @Author Mr.Li
     * @Date 2020/2/18 22:17
     */
    @Transactional
    public Boolean studentAddCurse(Integer curseId, String studentId,Integer number) {
        if (curseId == null||number==null||StringUtils.isBlank(studentId)) {
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        }
        int i1 = curseMapper1.whetherChoose(curseId,studentId);
        if (i1 > 0) {
            throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
        }
        int i = curseMapper1.studentAddCurse(studentId,curseId);
        if (i>0) {
                int i2 = curseMapper1.updateNumberByCurseId(curseId);
                if (i2 > 0) {
                    return true;
                }else{
                    throw new MySignException(ExceptionEnum.THE_NUMBER_HAS_REACHED);
                }
        }
        throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);

    }

    public Map<Object,Object> getCurse(String studentId, String teacherId) {
        if (StringUtils.isAllBlank(teacherId, studentId))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        List<curse> Curses = null;
        List<curse> AllCurses=null;//存放今天没课的课程集合
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String format = simpleDateFormat.format(date);//获取当前星期数

        if (StringUtils.isNotBlank(studentId)) {
            Curses = curseMapper1.getStudentCurses(studentId);
        } else if (StringUtils.isNotBlank(teacherId)) {
            Curses = curseMapper1.getTeacherCurses(teacherId);
        }
        Map<Object,Object> map=new HashMap();
int text=1;
        for (curse curs : Curses) {

            List<String> list=new ArrayList<>();//存放今天有课的课程集合
            String times = curs.getTimes();
            log.info("获取到的课程为：{}",times);
            String[] split = StringUtils.split(times, ",");//将数据中不同星期的课程分开
            log.debug("截取,结果为：{}",split);
            for (String s : split) {//遍历课程将星期与课程节时分开
                String[] split1 = StringUtils.split(StringUtils.substring(s,1,-1), ".");//去除括号
                log.info("去除. 号和括号之后为：{}",split1);
                if (StringUtils.equals(format, getWeek(split1[0]))) {//如果当前星期和数据库保存的星期相等（今天有课）
                    for (int i = 1; i < split1.length; i++) {
                        String curseNumber = getCurseNumber(split1[i],date);
                        if (curseNumber!=null)
                        map.put(curseNumber+String.valueOf(text),curs);
                    }
                    break;
                }
            }
            text++;
        }
        map.put("今天没课",Curses);
        if (CollectionUtils.isEmpty(Curses))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        return map;
    }

    @Autowired
     datetime datetime;

    public String getCurseNumber(String curseNumber,Date date) {
        int i = Integer.parseInt(curseNumber);
        String[] strings = {datetime.getZero(),datetime.getFirst(), datetime.getSecond(), datetime.getThird(), datetime.getFourth(), datetime.getFifth(), datetime.getSixth()
                , datetime.getSeventh(), datetime.getEighth(), datetime.getNinth(), datetime.getTenth(), datetime.getEleventh()};
        return strings[i-1];
    }
    public Boolean TeacherAddCurse(curse curse) {
       return curseMapper1.insertSelective(curse)>0;
    }

    public Map<String, Object> getAllCurseMessage() {
        ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<>();
        List<curse> lists = curseMapper1.selectAll();
        System.out.println("lists = " + lists);
        List<teacherInfo> allTeachersByCurse = curseMapper1.getAllTeachersByCurse();
        hashMap.put("全部教师", allTeachersByCurse);
        hashMap.put("全部课程", lists);
        return hashMap;
    }

    public static void main(String[] args) {
        String[] s = StringUtils.split("(4.8),(7.9.10)", ",");
        String week = "";
        for (String s1 : s) {
            s1 = StringUtils.substring(s1, 1, -1);
            System.out.println("s1 = " + s1);
            String[] s2 = StringUtils.split(s1, ".");
            System.out.println("s2 = " + s2);
            curseService service = new curseService();
            week = week + service.getWeek(s2[0]);
            for (int i = 1; i < s2.length; i++) {
                week = week + "第" + s2[i] + "节";
            }
            week = week + ",";
        }
        System.out.println(StringUtils.substring(week, 0, -1));
    }
   public List<teacherInfo> findTeacherByCurseName(String curseName){
        if (StringUtils.isNotBlank(curseName)){
            List<teacherInfo> teacherByCurseName = curseMapper1.findTeacherByCurseName(curseName);
            if (teacherByCurseName!=null)
                return teacherByCurseName;
            throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
        }
        throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
    }
}