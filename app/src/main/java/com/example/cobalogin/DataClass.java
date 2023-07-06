package com.example.cobalogin;

public class DataClass {

    private String name;
    private String venue;
    private String date;
    private String price;
    private String desc;
    private String image;

    public DataClass(String name, String venue, String date, String price, String desc, String imgURL) {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.price = price;
        this.desc = desc;
        this.image = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DataClass(){

    }
}

