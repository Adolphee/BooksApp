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
import com.android.adolphe.booksapp.Adapters.CollectionAdapter;
import com.android.adolphe.booksapp.Database.CollectionDAO;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.R;

import java.util.ArrayList;

public class CollectionFragment extends Fragment {

    private View view;
    private RecyclerView rv;
    private CollectionAdapter adapter;
    ArrayList<Book> bookCollection;
    CatalogFragment.CatalogListener listener;
    public CollectionFragment() {
    }

    public void setListener(CatalogFragment.CatalogListener activity){
        listener = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        Button btn_search = (Button) view.findViewById(R.id.searchBtn_collection);
        final MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity.getCollection() == null){
            CollectionDAO dao = new CollectionDAO(getActivity().getApplicationContext());
            bookCollection = dao.getCollection();
            mainActivity.setCollection(bookCollection);
        } else bookCollection = mainActivity.getCollection();

        btn_search.setOnClickListener(new View.OnClickListener() {
            TextView tv_search_text = (TextView) view.findViewById(R.id.searchText_collection);
            @Override
            public void onClick(View v) {
                CollectionDAO dao = new CollectionDAO(getActivity().getApplicationContext());
                bookCollection = dao.getCollection();
                ArrayList<Book> searchResults = new ArrayList<>();
               if(tv_search_text.getText().toString() != null && !tv_search_text.getText().toString().isEmpty()){
                   if(bookCollection != null){
                       for(Book b: bookCollection) {
                           if(b.getVolumeInfo().getTitle().toLowerCase()
                                   .contains(tv_search_text.getText().toString().toLowerCase())){
                               searchResults.add(b);
                           }
                       }
                   }
                   bookCollection = searchResults;
               }
               updateView();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_collection);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.fillAndCallCatalogFragment();
            }
        });
        updateView();
        return view;
    }

    public void updateView(){
        rv = view.findViewById(R.id.recyclerview_collection);
        adapter = new CollectionAdapter(this, getActivity().getApplicationContext(), bookCollection);
        boolean isInLandscapeMode = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), isInLandscapeMode? 3:2));
        rv.setAdapter(adapter);
    }
}
