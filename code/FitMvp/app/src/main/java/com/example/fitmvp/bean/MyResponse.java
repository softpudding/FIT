package com.example.fitmvp.bean;

public class MyResponse<T> {

    /**
     * result : 102
     * user : {"isactive":false,"password":"b","tel":"103"}
     * token : No token!
     */

    private String result;
    private T user;
    private String token;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
