package com.example.adolphe.booksapp.Model;

public class Book {

    private String title    ;
    private String subtitle ;
    private String authors  ;
    private double rating   ;
    private String url      ;
    private String imgUrl   ;

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
}

