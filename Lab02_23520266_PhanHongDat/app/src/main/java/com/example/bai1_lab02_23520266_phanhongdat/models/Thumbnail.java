package com.example.bai1_lab02_23520266_phanhongdat.models;

import com.example.bai1_lab02_23520266_phanhongdat.R;

public enum Thumbnail {
    THUMNAIL1("Thumnail1", R.drawable.first_thumnail),
    THUMNAIL2("Thumnail2", R.drawable.second_thumnail),
    THUMNAIL3("Thumnail3", R.drawable.third_thumnail),
    THUMNAIL4("Thumnail4", R.drawable.fourth_thumnail);

    private String name;
    private int imageResID;
    Thumbnail(String name, int imageResID) {
        this.name = name;
        this.imageResID = imageResID;
    }
    public String getName() {
        return name;
    }
    public int getImageResID() {
        return imageResID;
    }

}