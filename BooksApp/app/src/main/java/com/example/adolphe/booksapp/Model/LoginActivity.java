package com.example.adolphe.booksapp.Model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adolphe.booksapp.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        books = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int j = i + 1;
            Book book = new Book(
                    "Title " + j,
                    "Subtitle " + j,
                    "Author " + j,
                    round(j % 4.9, 1),
                    "",
                    "");

            books.add(book);
        }

        RecyclerView rv = findViewById(R.id.recyclerview);
        CatalogAdapter adapter = new CatalogAdapter(this, books);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(adapter);
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}


