package com.example.navifationtest;

public class Teacher {
    private String Nametext;
    private String Titletext;
    private int imageId;

    public Teacher (String Nametext, String Titletext, int imageId) {
        this.Nametext = Nametext;
        this.Titletext = Titletext;
        this.imageId = imageId;
    }

    public String Nametext() {
        return Nametext;
    }

    public String Titletext() {
        return Titletext;
    }

    public int getImageId() {
        return imageId;
    }
}
