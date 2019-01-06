package com.example.adolphe.booksapp.API;

import com.example.adolphe.booksapp.Model.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RFEndpointInterface {
    // Asynchronous declaration
    @GET("volumes?filter=ebooks&q=intitle:") //?q=intitle:{title}&filter:ebooks
    Call<Data> getBooks();

    @GET("volumes")
    Call<Data> getBooks(@Query("q") String title);
}
