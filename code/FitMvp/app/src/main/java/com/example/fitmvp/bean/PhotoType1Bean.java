package com.example.fitmvp.bean;


import com.google.gson.annotations.SerializedName;

public class PhotoType1Bean {
    @SerializedName("class")
    private String foodname;
    private Float probability;

    public String getFoodname() {
        return foodname;
    }

    public Float getProbability() {
        return probability;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }
}
