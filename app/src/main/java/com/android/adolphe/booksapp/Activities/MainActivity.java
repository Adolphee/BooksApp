package com.android.adolphe.booksapp.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.adolphe.booksapp.API.RFEndpointInterface;
import com.android.adolphe.booksapp.API.RetroFitSingleton;
import com.android.adolphe.booksapp.Fragments.BookDetailFragment;
import com.android.adolphe.booksapp.Fragments.CatalogFragment;
import com.android.adolphe.booksapp.Fragments.CollectionFragment;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.Adapters.CatalogAdapter;
import com.android.adolphe.booksapp.Models.Data;
import com.android.adolphe.booksapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CatalogFragment.CatalogListener {

    public static ArrayList<Book> books;
    public static ArrayList<Book> bookCollection;
    private RecyclerView rv;
    private int currentFragment;

    private DrawerLayout drawer;

    @Override
    public void onBookDetailsRequest(Book book, boolean fromCatalog) {
        if(fromCatalog){
            BookDetailFragment detailFragment = new BookDetailFragment();
            Bundle b = new Bundle();
            b.putString("book_json", new Gson().toJson(book));
            b.putBoolean("fromCatalog", true);
            detailFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
            intent.putExtra("book_json", new Gson().toJson(book));
            startActivity(intent);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof CatalogFragment){
            CatalogFragment catalogFragment = (CatalogFragment) fragment;
            catalogFragment.setListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(getIntent().getExtras() == null){
            handleSearch("Summer Of Anger"); // dus dit zou niet nodig zijn
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(getIntent().getExtras() != null){
            Intent intent = getIntent();
            if(intent.getExtras().getString("book_json") != null){
                changeToFragmentWithId(R.id.nav_collection);
            }
        } else if(savedInstanceState == null) {
            CatalogFragment catalogFragment = new CatalogFragment();
            catalogFragment.setListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, catalogFragment)
                    .commit();
            currentFragment = R.id.nav_catalog;
        }
    }

    public void handleSearch(String searchText){
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
                    findViewById(R.id.no_books_msg ).setVisibility(View.VISIBLE);
                else  findViewById(R.id.no_books_msg).setVisibility(View.INVISIBLE);
                rv = findViewById(R.id.recyclerview);
                CatalogAdapter adapter = new CatalogAdapter(getApplicationContext(), books);
                boolean isInLandscapeMode = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), isInLandscapeMode? 3:2));
                rv.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void fillAndCallCatalogFragment() {
        CatalogFragment catalogFragment = new CatalogFragment();
        if(books != null && books.size() > 0){
            Bundle b = new Bundle();
            ArrayList<String> jsonBookList = new ArrayList<>();
            for(Book book: books){
                jsonBookList.add(new Gson().toJson(book));
            }
            b.putStringArrayList("jsonBooks", jsonBookList);
            catalogFragment.setArguments(b);
        }
        catalogFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, books.size() > 0? catalogFragment: new CatalogFragment())
                .commit();
        currentFragment = R.id.catalog_fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        changeToFragmentWithId(id);
        return true;
    }

    public void changeToFragmentWithId(int id) {
        if(id == R.id.nav_collection && currentFragment != id) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CollectionFragment())
                    .commit();
        } else  if(id == R.id.nav_catalog && currentFragment != id) {
            fillAndCallCatalogFragment();
        } else if(id == R.id.nav_share) {
            Toast.makeText(this,getString(R.string.share), Toast.LENGTH_SHORT).show();
        } else if(id == R.id.nav_send){
            Toast.makeText(this,getString(R.string.send), Toast.LENGTH_SHORT).show();
        }

        if(currentFragment != id){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            currentFragment = id;
        }
    }

    public void setCollection(ArrayList<Book> bookCollection) {
        this.bookCollection = bookCollection;
    }

    public ArrayList<Book> getCollection() {
        return bookCollection;
    }
}
