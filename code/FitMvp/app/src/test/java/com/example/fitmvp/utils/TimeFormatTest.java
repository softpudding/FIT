package com.example.fitmvp.utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.jiguang.api.JCoreInterface;

import static org.junit.Assert.*;

public class TimeFormatTest {
    private TimeFormat timeFormat;

    @Test
    public void getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        // 往年
        Date date = null;
        try{
            date = formatter.parse("2018-08-01 00:00:00");
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }
        testEqual("2018-8-1",date);

        // 今年
        date = new Date();
        String nowStr = formatter.format(date);
        calendar.setTime(date);
        int month = Integer.parseInt(nowStr.substring(5, 7));
        int day = Integer.parseInt(nowStr.substring(8, 10));
        if(month<=2){
            calendar.add(calendar.MONTH, 2);
        }
        else{
            calendar.add(calendar.MONTH, -2);
        }
        date = calendar.getTime();
        String expected = formatter.format(date);
        month = Integer.parseInt(expected.substring(5,7));
        day = Integer.parseInt(expected.substring(8,10));
        testEqual(month+"-"+day, date);

        // 今天
        date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        testEqual("00:00", date);

        // 昨天
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        testEqual("昨天",date);

        // 前天
        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        testEqual("前天",date);

        // 3-7天前
        for(int i = 0; i < 5; i++){
            calendar.add(calendar.DATE,-1);
            date = calendar.getTime();
            testSubString("周",date);
        }

        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        testSubString("-",date);
    }

    private void testEqual(String expected, Date date){
        timeFormat = new TimeFormat(null,date.getTime());
        String time = timeFormat.getTime();
        assertEquals(expected,time);
    }

    private void testSubString(String expected, Date date){
        timeFormat = new TimeFormat(null,date.getTime());
        String time = timeFormat.getTime();
        assertTrue(time.contains(expected));
    }

    private void testSubStringDetail(String expected, Date date){
        timeFormat = new TimeFormat(null,date.getTime());
        String time = timeFormat.getDetailTime();
        assertTrue(time.contains(expected));
    }

    @Test
    public void getDetailTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        // 往年
        Date date = null;
        try{
            date = formatter.parse("2018-08-01 00:00:00");
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }
        testSubStringDetail("2018-8-1",date);

        // 今年
        date = new Date();
        String nowStr = formatter.format(date);
        calendar.setTime(date);
        int month = Integer.parseInt(nowStr.substring(5, 7));
        int day = Integer.parseInt(nowStr.substring(8, 10));
        if(month<=2){
            calendar.add(calendar.MONTH, 2);
        }
        else{
            calendar.add(calendar.MONTH, -2);
        }
        date = calendar.getTime();
        String expected = formatter.format(date);
        month = Integer.parseInt(expected.substring(5,7));
        day = Integer.parseInt(expected.substring(8,10));
        testSubStringDetail(month+"-"+day, date);

        // 今天
        date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        testSubStringDetail("00:00", date);

        // 昨天
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        testSubStringDetail("昨天",date);

        // 前天
        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        testSubStringDetail("前天",date);

        // 3-7天前
        for(int i = 0; i < 5; i++){
            calendar.add(calendar.DATE,-1);
            date = calendar.getTime();
            testSubStringDetail("周",date);
        }

        calendar.add(calendar.DATE,-1);
        date = calendar.getTime();
        testSubStringDetail("-",date);
    }

    @Test
    public void format() {
        long today = JCoreInterface.getReportTime();
        java.sql.Date now = new java.sql.Date(today*1000);
        timeFormat = new TimeFormat(null,now.getTime());
        String nowStr = timeFormat.format(now,"yyyy-MM-dd HH:mm:ss");
        assertEquals(19,nowStr.length());
        assertTrue(nowStr.contains("-"));
        assertTrue(nowStr.contains(" "));
        assertTrue(nowStr.contains(":"));
    }
}