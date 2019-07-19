package com.example.fitmvp.bean;

import com.google.gson.annotations.SerializedName;

public class TypeTwo {
        @SerializedName("obj_type")
        private Integer objtype;
        private String img;
        private Integer plate_type;

        public Integer getObjtype() {
            return objtype;
        }
        public String getImg() {
            return img;
        }

    public Integer getPlate_type() {
        return plate_type;
    }

    public void setImg(String img) {
            this.img = img;
        }

        public void setObjtype(Integer obj_type) {
            this.objtype = obj_type;
        }

    public void setPlate_type(Integer plate_type) {
        this.plate_type = plate_type;
    }
}
