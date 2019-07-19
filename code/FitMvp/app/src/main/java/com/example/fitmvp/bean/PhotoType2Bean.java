package com.example.fitmvp.bean;

import com.google.gson.annotations.SerializedName;

public class PhotoType2Bean {
    @SerializedName("class")
    private String foodname;
    private Float probsbility;

    public String getFoodname() {
        return foodname;
    }

    public Float getProbsbility() {
        return probsbility;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setProbsbility(Float probsbility) {
        this.probsbility = probsbility;
    }
}
