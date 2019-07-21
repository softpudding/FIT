package com.example.fitmvp.bean;

import com.example.fitmvp.utils.LogUtils;

public class ConversationEntity {
    /**
     * avater :
     * username :
     * message :
     * time :
     * newMsgNum : 0
     */
    private String Id;
    private String avatar;
    private String username;
    private String title;
    private String message;
    private String time;
    // 排序用
    private Long rawTime;
    private int newMsgNum;

    public String getId(){
        return Id;
    }

    public void setId(String id){
        this.Id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getRawTime(){
        return rawTime;
    }

    public void setRawTime(Long time){
        rawTime = time;
    }
    public int getNewMsgNum() {
        return newMsgNum;
    }

    public void setNewMsgNum(int newMsgNum) {
        this.newMsgNum = newMsgNum;
    }
}
