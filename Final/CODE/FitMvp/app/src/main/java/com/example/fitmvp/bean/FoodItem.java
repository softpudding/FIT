package com.example.fitmvp.bean;

import android.graphics.Bitmap;

public class FoodItem {
    private Bitmap bitmap;
    private String foodname;
    private Integer weight;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getFoodname() {
        return foodname;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
