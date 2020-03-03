package com.mysign.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/14 23:19
 */
public class getCurseTime {
    /**
    *@Description: 解析课程 将课程时间解析成字符串
    *@Params
    *@Return
    *@Author Mr.Li
    *@Date 2020/2/15 0:43
    */
    public  String getCurseTime(String curseTime){
                String[] s = StringUtils.split(curseTime, ".");
        String week=null;
                for (String s1 : s) {
                    char[] chars = s1.toCharArray();
                    week = getWeek(String.valueOf(chars[1]));
                    for (int j = 3; j < chars.length; j = j + 2) {
                        week = week + "第"+String.valueOf(chars[j])+"节";


                    }
                }
                return week;
            }
/**
*@Description: 生成星期数组
*@Params
*@Return
*@Author Mr.Li
*@Date 2020/2/15 0:44
*/
            public  String getWeek(String week){
        int w=Integer.parseInt(week);
        String[] strings={"周一","周二","周三","周四","周五","周六","周日"};
        return strings[w-1];
            }



}
