package com.example.fitmvp.bean;

import com.example.fitmvp.utils.TimeFormat;

import java.sql.Timestamp;

public class NoticeBean {

    /**
     * id : 1
     * time_stamp : 2019-07-30 15:00:05
     * tittle : test1
     * news : news1
     * active : 1
     */

    private int id;
    private String time_stamp;
    private String tittle;
    private String news;
    private int active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_stamp() {
        Timestamp time_s = Timestamp.valueOf(time_stamp);
        long time_l = time_s.getTime();
        TimeFormat timeFormat = new TimeFormat(null,time_l);
        return timeFormat.getDetailTime();
    }

    public long getRawTime(){
        Timestamp time_s = Timestamp.valueOf(time_stamp);
        return time_s.getTime();
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
