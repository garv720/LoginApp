package com.example.gaurav.myloginapp;

public class Marks {
    private String email, maths, physics, chemistry, fcp, bme;

    public Marks(String email, String maths, String physics, String chemistry, String fcp, String bme) {
        this.email = email;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
        this.fcp = fcp;
        this.bme = bme;
    }

    public Marks(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaths() {
        return maths;
    }

    public void setMaths(String maths) {
        this.maths = maths;
    }

    public String getPhysics() {
        return physics;
    }

    public void setPhysics(String physics) {
        this.physics = physics;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    public String getFcp() {
        return fcp;
    }

    public void setFcp(String fcp) {
        this.fcp = fcp;
    }

    public String getBme() {
        return bme;
    }

    public void setBme(String bme) {
        this.bme = bme;
    }
}
