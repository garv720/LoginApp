package com.example.gaurav.myloginapp;

public class Teacher {
    private int id;
    private String name, department,dob,email,password;

    public Teacher(int id, String name, String department, String dob, String email, String password) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }

    public Teacher(){

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
}
