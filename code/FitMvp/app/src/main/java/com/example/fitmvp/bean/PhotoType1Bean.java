package com.example.fitmvp.bean;


import com.google.gson.annotations.SerializedName;

public class PhotoType1Bean {
    @SerializedName("class")
    private String foodname;
    private Double probsbility;

    public String getFoodname() {
        return foodname;
    }

    public Double getProbsbility() {
        return probsbility;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setProbsbility(Double probsbility) {
        this.probsbility = probsbility;
    }
}
