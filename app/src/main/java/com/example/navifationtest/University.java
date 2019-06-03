package com.example.navifationtest;

public class University {
    private String name;
    private int imageId;
    public University(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return  imageId;
    }

}