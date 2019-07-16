package com.example.fitmvp.bean;

public class RecordItem {
    private Integer image;
    private String title;
    private String text;

    public Integer getImage(){
        return image;
    }
    public void setImage(Integer img){
        image = img;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }
}
