package com.example.adolphe.booksapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adolphe.booksapp.API.RFEndpointInterface;
import com.example.adolphe.booksapp.API.RetroFitSingleton;
import com.example.adolphe.booksapp.Model.Book;
import com.example.adolphe.booksapp.Model.CatalogAdapter;
import com.example.adolphe.booksapp.Model.Data;
import com.example.adolphe.booksapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CatalogActivity extends AppCompatActivity {

    public ArrayList<Book> books;
    RecyclerView rv;
    CatalogAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_activity);
        books = new ArrayList<Book>();
        Button btn_search = (Button) findViewById(R.id.searchBtn);
        TextView tv_search_text = (TextView) findViewById(R.id.searchText);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearch(tv_search_text.getText().toString());
            }
        });
        handleSearch("Summer Of Anger");
        rv = findViewById(R.id.recyclerview);
        adapter = new CatalogAdapter(getApplicationContext(), books);
        rv.setLayoutManager(new GridLayoutManager(CatalogActivity.super.getApplicationContext(), 2));
        rv.setAdapter(adapter);
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }



    private void handleSearch(String searchText){
        Retrofit retrofit = RetroFitSingleton.getInstance();

        RFEndpointInterface googleBooks = retrofit.create(RFEndpointInterface.class);

        Call<Data> call = googleBooks.getBooks( "intitle:" + searchText);

        call.enqueue(new Callback<Data>() { // hier kies ik voor het gebruik van een AsyncTask
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(!response.isSuccessful()){
                    return;
                }

                books = (ArrayList<Book>) response.body().getItems();


                if(books == null || books.size() == 0)
                    findViewById(R.id.no_books_msg).setVisibility(View.VISIBLE);
                else  findViewById(R.id.no_books_msg).setVisibility(View.INVISIBLE);
                if(rv != null){
                    adapter = new CatalogAdapter(getApplicationContext(), books);
                    rv.setAdapter(adapter);
                } else {
                    rv = findViewById(R.id.recyclerview);
                    adapter = new CatalogAdapter(getApplicationContext(), books);
                    rv.setLayoutManager(new GridLayoutManager(CatalogActivity.super.getApplicationContext(), 2));
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                return;
            }
        });
    }
}


