package com.android.adolphe.booksapp.API;

import com.android.adolphe.booksapp.Models.Data;

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
