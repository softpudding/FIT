package com.example.fitmvp.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.google.gson.JsonArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PhotoTypetBean {
    private JSONArray predictions;
    private JSONArray boxes;
//    private List<PhotoType1Bean> predictions = new ArrayList<>();
//    private List<box> boxes = new ArrayList<>();

    public void setBoxes(JSONArray boxes) {
        this.boxes = boxes;
    }

    public void setPredictions(JSONArray predictions) {
        this.predictions = predictions;
    }

    public JSONArray getBoxes() {
        return boxes;
    }

    public JSONArray getPredictions() {
        return predictions;
    }
}
