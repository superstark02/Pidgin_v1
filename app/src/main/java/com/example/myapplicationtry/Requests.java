package com.example.myapplicationtry;

public class Requests {
    private String name;
    private String profilePic;
    private String date;
    private String time;

    public Requests(){}

    public Requests (String  name, String profilePic, String date, String time){
        this.name = name;
        this.profilePic = profilePic;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
