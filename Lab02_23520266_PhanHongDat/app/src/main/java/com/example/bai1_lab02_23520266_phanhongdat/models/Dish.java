package com.example.bai1_lab02_23520266_phanhongdat.models;
public class Dish {
    private String name;
    private int thumnailResId;
    private Boolean isPromotion;
    public Dish(String name,int thumnailResId, boolean isPromotion)
    {
        this.name=name;
        this.thumnailResId=thumnailResId;
        this.isPromotion=isPromotion;
    }
    public String getName()
    {
        return name;
    }
    public int getThumnailResId()
    {
        return thumnailResId;
    }
    public Boolean isPromotion()
    {
        return isPromotion;
    }

}