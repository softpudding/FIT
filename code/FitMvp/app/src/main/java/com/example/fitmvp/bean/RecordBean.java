package com.example.fitmvp.bean;

import com.example.fitmvp.utils.TimeFormat;

import java.sql.Timestamp;

public class RecordBean {
    /**
     * timeStamp : 2019-07-26 14:55:46 下午
     * protein : 0.0
     * fat : 0.0
     * weight : 500.0
     * tel : 110
     * id : 1
     * if_is_vegetable : false
     * carbohydrate : 0.0
     * food : 米饭
     * cal : 1.0
     */

    private String timeStamp;
    private double protein;
    private double fat;
    private double weight;
    private String tel;
    private int id;
    private boolean if_is_vegetable;
    private double carbohydrate;
    private String food;
    private double cal;

    public String getTimeStamp() {
        Timestamp time_s = Timestamp.valueOf(timeStamp);
        long time_l = time_s.getTime();
        TimeFormat timeFormat = new TimeFormat(null,time_l);
        return timeFormat.getDetailTime();
    }

    public long getRawTime(){
        Timestamp time_s = Timestamp.valueOf(timeStamp);
        return time_s.getTime();
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIf_is_vegetable() {
        return if_is_vegetable;
    }

    public void setIf_is_vegetable(boolean if_is_vegetable) {
        this.if_is_vegetable = if_is_vegetable;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }
}
