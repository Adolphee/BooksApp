package com.example.adolphe.booksapp.Model;

public class Book {

    private String title    ;
    private String subtitle ;
    private String authors  ;
    private double rating   ;
    private String url      ;
    private String imgUrl   ;
    private String description;
    private Double price    ;
    private String category;

    public Book(String title, String subtitle, String authors, double rating, String url, String imgUrl) {
        this.title      = title;
        this.subtitle   = subtitle;
        this.authors    = authors;
        this.rating     = rating;
        this.url        = url;
        this.imgUrl     = imgUrl;
    }

    public String getAuthors() {  return  this.authors; }
    public String getTitle() { return this.title; }
    public String getSubtitle() { return this.subtitle; }
    public double getRating() {return this.rating; }
    public String getUrl() { return this.url ; }
    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public String getDescription(){ return description; }
    public void setDescription(String desc){ this.description = desc; }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

