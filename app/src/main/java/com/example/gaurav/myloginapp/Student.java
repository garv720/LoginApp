package com.example.gaurav.myloginapp;

public class Student {

    private int id;
    private String name;
    private String branch;
    private String dob;
    private String email;
    private String password;
    private String marks;

    public Student(int id, String name, String branch, String dob, String email, String password, String marks) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.marks = marks;
    }

    public Student(){

    }

    public int getId() {
        return id;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}

