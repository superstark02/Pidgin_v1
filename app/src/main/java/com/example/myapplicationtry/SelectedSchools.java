package com.example.myapplicationtry;

public class SelectedSchools {
    private String name;
    private int fees;
    private String profilePic;
    private String classes;

    public SelectedSchools() {
    }

    public SelectedSchools(String name, String profilePic, int fees,String classes) {

        this.name = name;
        this.profilePic = profilePic;
        this.fees = fees;
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
