package com.healthcareapp.myapplication;

import java.util.UUID;

public class DBItem {

    String id = UUID.randomUUID().toString();
    String name;
    String categories;
    String imageURL ="" ;

    public DBItem() {}

    public DBItem(String name, String categories, String imageURL) {
        this.name = name;
        this.categories = categories;
        this.id = id;
        this.imageURL = imageURL;
    }

    public String getName() {

        return name;
    }

    public String getCategories() {
        return categories;
    }
    public  String getID() {
        return id;
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
