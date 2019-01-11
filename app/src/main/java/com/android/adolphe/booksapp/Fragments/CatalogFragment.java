package com.android.adolphe.booksapp.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.adolphe.booksapp.Activities.MainActivity;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.Adapters.CatalogAdapter;
import com.android.adolphe.booksapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CatalogFragment extends Fragment {
    private View view;
    private RecyclerView rv;
    private CatalogAdapter adapter;
    ArrayList<Book> books;
    CatalogListener listener;

    public interface CatalogListener {
        public void onBookDetailsRequest(Book book, boolean fromCatalog);
    }

    public CatalogFragment() {
    }

    public void setListener(CatalogListener activity){
        listener = activity;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        Button btn_search = (Button) view.findViewById(R.id.searchBtn);
        btn_search.setOnClickListener(new View.OnClickListener() {
            TextView tv_search_text = (TextView) view.findViewById(R.id.searchText);
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).handleSearch(tv_search_text.getText().toString());
            }
        });

       if(getArguments() != null){
           Bundle b = getArguments();
           ArrayList<String> jsonBooks = getArguments().getStringArrayList("jsonBooks");
           ArrayList<Book> receivedBooks = new ArrayList<>();
           for(String book: jsonBooks){
               receivedBooks.add(new Gson().fromJson(book, Book.class));
           }
           books = receivedBooks;
       }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeToFragmentWithId(R.id.nav_collection);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        updateView();
        return view;
    }

    public void updateView(){
        rv = view.findViewById(R.id.recyclerview);
        adapter = new CatalogAdapter(this, getActivity().getApplicationContext(), MainActivity.books);
        boolean isInLandscapeMode = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), isInLandscapeMode? 3:2));
        rv.setAdapter(adapter);
    }
}
