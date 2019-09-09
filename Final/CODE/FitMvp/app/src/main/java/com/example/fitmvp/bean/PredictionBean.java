package com.example.fitmvp.bean;

import com.google.gson.annotations.SerializedName;

public class PredictionBean {
    @SerializedName("class")
    private String foodname;
    private float probability;

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public String getFoodname() {
        return foodname;
    }

    public float getProbability() {
        return probability;
    }
}
