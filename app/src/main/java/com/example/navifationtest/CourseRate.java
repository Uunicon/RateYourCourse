package com.example.navifationtest;

public class CourseRate {
    private String RateName;
    private String RateValue;
    public CourseRate(String RateName, String RateValue){
        this.RateName=RateName;
        this.RateValue=RateValue;
    }
    public String getRateName(){
        return RateName;
    }
    public String getRateValue(){
        return RateValue;
    }
}
