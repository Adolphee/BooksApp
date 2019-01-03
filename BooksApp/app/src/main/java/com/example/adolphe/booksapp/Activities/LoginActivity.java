package com.example.adolphe.booksapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adolphe.booksapp.Model.Book;
import com.example.adolphe.booksapp.Model.CatalogAdapter;
import com.example.adolphe.booksapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                    "https://picsum.photos/400/600/?image=" + (int)(Math.random() * 1000 + 1));

            book.setDescription("Description " + j + "\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            book.setCategory("Category " + j);
            book.setPrice(4.95 + j);
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


