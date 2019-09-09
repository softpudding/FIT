package com.example.fitmvp.bean;


public class PhotoType1Bean<T> {
    private T prediction;
   // private Y nutri;

    public void setPrediction(T prediction) {
        this.prediction = prediction;
    }

//    public void setNutri(Y nutri) {
//        this.nutri = nutri;
//    }

//    public Y getNutri() {
//        return nutri;
//    }

    public T getPrediction() {
        return prediction;
    }
}
