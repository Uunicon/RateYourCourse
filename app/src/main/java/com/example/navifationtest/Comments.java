package com.example.navifationtest;

public class Comments {
    private String text;
    private int imageId;

    public Comments(String text, int imageId) {
        this.text = text;
        this.imageId = imageId;
    }

    public String getText() {
        return text;
    }

    public int getImageId() {
        return imageId;
    }
}