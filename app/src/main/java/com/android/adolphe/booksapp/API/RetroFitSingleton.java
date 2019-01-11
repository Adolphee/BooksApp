package com.android.adolphe.booksapp.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitSingleton {
    private static Retrofit retrofit;
    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";
    // https://www.googleapis.com/books/v1/volumes?q=intitle:flowers&filter=ebooks

    private static final String API_KEY = "AIzaSyA5y4ANBjy0gmK8jYUbsEyiTUlBmWY1lTI";

    public static Retrofit getInstance(){
        if(retrofit == null) {
            // Trailing slash is needed
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static void main(String[] args){
        RFEndpointInterface apiService =
                RetroFitSingleton.getInstance().create(RFEndpointInterface.class);
    }
}
