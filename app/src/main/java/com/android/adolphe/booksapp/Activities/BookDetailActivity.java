package com.android.adolphe.booksapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.adolphe.booksapp.Database.CollectionDAO;
import com.android.adolphe.booksapp.Fragments.CollectionFragment;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.R;
import com.google.gson.Gson;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_description, tv_category, tv_author, tv_rating, tv_subtitle, tv_price;
    private ImageView iv_image;
    private Button btn_buy;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        tv_title = (TextView) findViewById(R.id.bookdetail_title);
        tv_author = (TextView) findViewById(R.id.bookdetail_author);
        tv_category = (TextView) findViewById(R.id.bookdetail_category);
        tv_description = (TextView) findViewById(R.id.bookdetail_desc);
        tv_rating = (TextView) findViewById(R.id.bookdetail_rating);
        tv_subtitle = (TextView) findViewById(R.id.bookdetail_subtitle);
        tv_price = (TextView) findViewById(R.id.bookdetail_price);
        iv_image = (ImageView) findViewById(R.id.bookdetail_img);
        btn_buy = (Button) findViewById(R.id.bookdetail_buyBtn);
        btn_buy.setText(getString(R.string.remove));
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonBook = new Gson().toJson(book);
                    if(jsonBook != null && !jsonBook.isEmpty()){
                        CollectionDAO dao = new CollectionDAO(getApplicationContext());
                        dao.removeBook(jsonBook);
                        MainActivity.bookCollection = dao.getCollection();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("book_json", new Gson().toJson(book));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        Toast.makeText(getApplicationContext() ,getString(R.string.removed_from_collection), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        getApplicationContext().startActivity(intent);
                    }
            }
        });

        tv_title.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_subtitle.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_author.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_description.setShadowLayer(3, 3, 3, Color.BLACK);

        Intent intent = getIntent();
        final String jsonBook = intent.getExtras().getString("book_json");
        book = new Gson().fromJson(jsonBook, Book.class);

        if(book.getImageBitmap() != null){
            iv_image.setImageBitmap(book.getImageBitmap());
        } else {
            iv_image.setImageResource(R.drawable.nature);
            iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        if(book.getVolumeInfo().getTitle() != null)
            tv_title.setText(book.getVolumeInfo().getTitle());
        else  tv_title.setText(getString(R.string.untitledBook));

        if(book.getVolumeInfo().getSubtitle() != null)
            tv_subtitle.setText(book.getVolumeInfo().getSubtitle());
        else  tv_subtitle.setText("");

        if(book.getVolumeInfo().getAuthors() != null)
            tv_author.setText(book.getVolumeInfo().getAuthorsAsString());
        else tv_author.setText(getString(R.string.anonymousAuthor));

        if(book.getVolumeInfo().getPublishedDate() != null)
            tv_rating.setText(book.getVolumeInfo().getPublishedDate());
        else tv_rating.setText(getString(R.string.unknown));

        if(book.getVolumeInfo().getCategories() != null)
            tv_category.setText(book.getVolumeInfo().getCategories().get(0));
        else  tv_category.setText(getString(R.string.other));

        // Ik heb de prijs al gecontroleerd op nullpointers in de implementatie van Book.getPriceAsString()
        // tv_price.setText(book.getPriceAsString());

        if(book.getSaleInfo().getRetailPrice() != null){
            tv_price.setText(book.getPriceAsString());
        }
        else if(book.getSaleInfo().getListPrice() != null){
            tv_price.setText(book.getPriceAsString());
        }
        else {
            tv_price.setText(R.string.free);
        };

        if(book.getVolumeInfo().getDescription() != null)
            tv_description.setText(book.getVolumeInfo().getDescription());
        else tv_description.setText(getString(R.string.mysteriousLookingBook));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_description.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_book_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("book_json", new Gson().toJson(book));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}