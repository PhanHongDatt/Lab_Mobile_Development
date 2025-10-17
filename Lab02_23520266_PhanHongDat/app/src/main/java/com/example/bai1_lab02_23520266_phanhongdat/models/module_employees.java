package com.example.bai1_lab02_23520266_phanhongdat.models;

public class module_employees{
    private String ID;
    private String fullname;
    private boolean isManager;

    public module_employees(String ID, String fullname, boolean isManager)
    {
        this.ID=ID;
        this.fullname=fullname;
        this.isManager=isManager;
    }
    public String getFullName()
    {
        return fullname;
    }
    public Boolean isManager()
    {
        return this.isManager;
    }

}