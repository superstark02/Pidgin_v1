package com.example.myapplicationtry;

public class ClassesCategories {
    private String image;
    private String keyword;

    public ClassesCategories() {
    }

    public ClassesCategories(String image,String keyword) {
      this.image = image;
      this.keyword = keyword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
