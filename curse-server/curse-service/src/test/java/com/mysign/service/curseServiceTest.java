package com.mysign.service;

import com.mysign.service.mapper.curseMapper;
import com.mysign.service.po.curse;
import com.mysign.service.po.datetime;
import com.mysign.service.service.curseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/2/22 9:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class curseServiceTest {

    @Autowired
    curseService curseService;
    @Autowired
    datetime datetime;
    @Autowired
    curseMapper curseMapper;
    @Test
    public void test(){
        Map<Object, Object> curseTable = curseService.getCurseTable(curseMapper.getStudentCurses("160510201"));
        for (Object value : curseTable.values()) {
            System.out.println(value);
        }
    }
}
