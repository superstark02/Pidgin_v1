package com.example.myapplicationtry;

public class Chat {
    private String user;
    private String company;

    public Chat(){}

    public Chat(String user, String company){
        this.company = company;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
