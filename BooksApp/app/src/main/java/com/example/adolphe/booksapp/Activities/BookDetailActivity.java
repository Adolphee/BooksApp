package com.example.adolphe.booksapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adolphe.booksapp.Model.Book;
import com.example.adolphe.booksapp.Model.DownloadImageTask;
import com.example.adolphe.booksapp.R;
import com.jgabrielfreitas.core.BlurImageView;
import com.yinglan.shadowimageview.RoundImageView;
import com.yinglan.shadowimageview.ShadowImageView;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_description, tv_category, tv_author, tv_rating, tv_subtitle, tv_price;
    private ShadowImageView iv_image;
    private BlurImageView iv_blurry_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tv_title = (TextView) findViewById(R.id.bookdetail_title);
        tv_author = (TextView) findViewById(R.id.bookdetail_author);
        tv_category = (TextView) findViewById(R.id.bookdetail_category);
        tv_description = (TextView) findViewById(R.id.bookdetail_desc);
        tv_rating = (TextView) findViewById(R.id.bookdetail_rating);
        tv_subtitle = (TextView) findViewById(R.id.bookdetail_subtitle);
        tv_price = (TextView) findViewById(R.id.bookdetail_price);
        iv_image = (ShadowImageView) findViewById(R.id.bookdetail_img);
        iv_blurry_image = (BlurImageView) findViewById(R.id.bookdetail_blurImage);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String subtitle = intent.getExtras().getString("Subtitle");
        String author = intent.getExtras().getString("Authors");
        String description = intent.getExtras().getString("Description");
        Double rating = intent.getExtras().getDouble("Rating");
        Double price = intent.getExtras().getDouble("Price");
        String imageUrl = intent.getExtras().getString("ImageUrl");
        String category = intent.getExtras().getString("Category");
        //Bitmap imageBitmap = (Bitmap) intent.getParcelableExtra("ImageBitmap");
        Book book = new Book(title, subtitle, author, rating, imageUrl, imageUrl);
        new DownloadImageTask(iv_image, iv_blurry_image, book).execute(imageUrl);

        tv_title.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_subtitle.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_author.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_description.setShadowLayer(3, 3, 3, Color.BLACK);

        tv_title.setText(title);
        tv_subtitle.setText(subtitle);
        tv_author.setText(author);
        tv_rating.setText(rating.toString());
        tv_category.setText(category);
        tv_price.setText(price.toString());
        tv_description.setText(description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_description.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
        }
    }
}
