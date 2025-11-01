package com.example.sqlite;

public class Student{
    private int id;
    private String studentCode;
    private String name;
    private String dateofBirth;
    private String studentClass;
    private String academicYear;
    private String factly;
    private String major;
    private String email;

    public Student(){};
    public Student(int id, String studentCode, String name,String studentClass,String dateofBirth, String academicYear, String factly,
                   String major) {
        this.id = id;
        this.name = name;
        this.dateofBirth=dateofBirth;
        this.studentClass = studentClass;
        this.academicYear = academicYear;
        this.factly = factly;
        this.major = major;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDateofBirth() {
        return dateofBirth;
    }
    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }
    public String getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
    public String getAcademicYear() {
        return academicYear;
    }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    public String getFactly() {
        return factly;
    }
    public void setFactly(String factly) {
        this.factly = factly;
    }
    public void setMajor(String major)
    {
        this.major=major;
    }
    public String getMajor() {
        return major;
    }
    public String getEmail() {
        if (academicYear != null && !academicYear.isEmpty() && id > 0) {
            return academicYear + id + "@gm.uit.edu.vn";
        }
        return "unknown@gm.uit.edu.vn";
    }

}
