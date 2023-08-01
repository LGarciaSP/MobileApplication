package com.healthcareapp.myapplication.Model;

public class Item {
    private String name;
    private String imageUrl;

    public Item (String name, String imageUrl){
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public Item() {

    }
    public String getname(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
