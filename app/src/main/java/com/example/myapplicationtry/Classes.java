package com.example.myapplicationtry;

public class Classes {
    private  boolean permission;
    private String name;
    private int rating;
    private String fees;
    private String email;
    private String profilePic;
    private String type;

    public Classes() {
    }
    public Classes(String name, String email, String profilePic, boolean permission , String fees, String type,int rating) {
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
        this.permission = permission;
        this.fees = fees;
        this.type = type;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
