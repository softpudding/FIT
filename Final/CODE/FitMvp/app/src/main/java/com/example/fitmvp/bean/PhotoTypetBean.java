package com.example.fitmvp.bean;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class PhotoTypetBean {
    private JSONArray predictions;
    private JSONArray boxes;
//    private JSONArray nutri;
//    private List<PhotoType1Bean> predictions = new ArrayList<>();
//    private List<box> boxes = new ArrayList<>();

    public void setBoxes(JSONArray boxes) {
        this.boxes = boxes;
    }

    public void setPredictions(JSONArray predictions) {
        this.predictions = predictions;
    }

//    public void setNutri(JSONArray nutri) {
//        this.nutri = nutri;
//    }

    public JSONArray getBoxes() {
        return boxes;
    }

    public JSONArray getPredictions() {
        return predictions;
    }

//    public JSONArray getNutri() {
//        return nutri;
//    }
}
