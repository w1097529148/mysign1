package com.mysign.service.service;

import com.mysign.service.Exception.MySignException;
import com.mysign.service.mapper.curseMapper;
import com.mysign.service.po.curse;
import com.mysign.service.po.curseTimes;
import com.mysign.service.po.datetime;
import com.mysign.service.po.teacherInfo;
import com.mysign.service.vo.ExceptionEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
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
    public List<curse> getCurseByName(String CurseName) {

        if (StringUtils.isBlank(CurseName))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        List<curse> curseByName = curseMapper1.getCurseByName("%" + CurseName + "%");
        return curseByName;
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
                String[] s = StringUtils.split(str, ".");
                for (String s1 : s) {
                    s1 = StringUtils.substring(s1, 1, -1);
                    String[] s2 = StringUtils.split(s1, ",");
                    k = Integer.parseInt(s2[0]);
                    for (int j = 1; j < s2.length; j++) {
                        index = Integer.parseInt(s2[j])-1;
                        if (index == i) {
                            String s3 = list.getCurseName() + list.getScore() + "分" + list.getWhethe() +""+ list.getWeek()+ list.getStartTime() + "~" + list.getEndTime() + list.getPlace();
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
    public Map<Object,Object> getCurseTimeAndCurse(String curseTime, Integer CurseId) {
        if (StringUtils.isBlank(curseTime) && CurseId == null)
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        Map map = new HashMap();
        Boolean b = true;
        int i1 = curseMapper1.whetherChoose(CurseId);
        if (i1 == 0) {
            b = false;
        }
        String[] s = StringUtils.split(curseTime, ".");
        String week = "";
        for (String s1 : s) {
            s1 = StringUtils.substring(s1, 1, -1);
            String[] s2 = StringUtils.split(s1, ",");
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
        System.out.println(studentId);
        System.out.println(teacherId);
        if (StringUtils.isBlank(studentId)&&StringUtils.isBlank(teacherId))
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        System.out.println("我过来了");
        List<teacherInfo> allTeachersByStudent = null;
        List<curse> CurseWithMe = null;//与我相关
        if (StringUtils.isNotBlank(studentId)) {
            allTeachersByStudent = curseMapper1.getAllTeachersByStudent(studentId);
            CurseWithMe = curseMapper1.getStudentCurses(studentId);
        }
        if (StringUtils.isNotBlank(teacherId))
            CurseWithMe = curseMapper1.getTeacherCurses(teacherId);
        List<curse> lists = curseMapper1.selectAll();

        List<teacherInfo> allTeachersByCurse = curseMapper1.getAllTeachersByCurse();

        if (!CollectionUtils.isEmpty(CurseWithMe) && !CollectionUtils.isEmpty(lists) && !CollectionUtils.isEmpty(allTeachersByCurse)) {
            Map<Object, Object> curseTable = new HashMap<>();
            if (!CollectionUtils.isEmpty(allTeachersByStudent))
                curseTable.put("我的教师", allTeachersByStudent);
            curseTable.put("与我相关", CurseWithMe);
            curseTable.put("全部教师", allTeachersByCurse);
            curseTable.put("全部课程", lists);
            return curseTable;
        }
        else{
            System.out.println("我抛异常");
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
    public Boolean studentAddCurse(Integer curseId, String studentId) {
        if (curseId == null) {
            throw new MySignException(ExceptionEnum.INPUT_IS_BLANK);
        }
        int i1 = curseMapper1.whetherChoose(curseId);
        if (i1 > 0) {
            throw new MySignException(ExceptionEnum.ROLE_IS_NOT_EXISTS);
        }
        int i = curseMapper1.studentAddCurse(curseId, studentId);
        if (i > 0)
            return true;
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
            String[] split = StringUtils.split(times, ".");//将数据中不同星期的课程分开
            for (String s : split) {//遍历课程将星期与课程节时分开
                String[] split1 = StringUtils.split(StringUtils.substring(s,1,-1), ",");//去除括号
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


}