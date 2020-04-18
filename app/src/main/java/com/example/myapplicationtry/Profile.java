package com.example.myapplicationtry;

import android.content.Intent;
import android.os.Bundle;

import java.util.Comparator;

public class Profile {
    private String name;
    private int fees;
    private String email;
    private String profilePic;
    private String url;
    private boolean permission;
    private double x;
    private double y;
    private double distance;
    private int image1;
    private int image2;
    private int image3;
    private int image4;
    private int image5;
    double longitude;
    double latitude;

    public Profile() {
    }

    public Profile(String name, String email, String profilePic, boolean permission, int fees, int x, int y,double distance,String url) {

        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
        this.permission = permission;
        this.fees = fees;
        this.x = x;
        this.y = y;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public int getFees(){
        return fees;
    }

    public void setFees(int fees){
        this.fees = fees;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage3() {
        return image3;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }

    public int getImage4() {
        return image4;
    }

    public void setImage4(int image4) {
        this.image4 = image4;
    }

    public int getImage5() {
        return image5;
    }

    public void setImage5(int image5) {
        this.image5 = image5;
    }

    public static Comparator<Profile> ByDistance = new Comparator<Profile>() {
        @Override
        public int compare(Profile o1, Profile o2) {
            return Double.valueOf(o1.distance).compareTo(Double.valueOf(o2.distance));
        }
    };
}
