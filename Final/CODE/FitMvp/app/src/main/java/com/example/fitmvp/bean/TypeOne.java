package com.example.fitmvp.bean;

import com.google.gson.annotations.SerializedName;

public class TypeOne {
    @SerializedName("obj_type")
    private Integer objtype;
    private String img;

    public Integer getObjtype() {
        return objtype;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setObjtype(Integer obj_type) {
        this.objtype = obj_type;
    }
}
