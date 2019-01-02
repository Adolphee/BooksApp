package com.example.adolphe.booksapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adolphe.booksapp.Model.DownloadImageTask;
import com.example.adolphe.booksapp.R;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_description, tv_category, tv_author, tv_rating, tv_subtitle, tv_price;
    private ImageView iv_image;

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
        iv_image = (ImageView) findViewById(R.id.bookdetail_img);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String subtitle = intent.getExtras().getString("Subtitle");
        String author = intent.getExtras().getString("Author");
        String description = intent.getExtras().getString("Description");
        Double rating = intent.getExtras().getDouble("Rating");
        Double price = intent.getExtras().getDouble("Price");
        String imageUrl = intent.getExtras().getString("ImageUrl");
        String category = intent.getExtras().getString("Category");

        tv_title.setText(title);
        tv_subtitle.setText(subtitle);
        tv_author.setText(author);
        tv_rating.setText(rating.toString());
        tv_category.setText(category);
        tv_price.setText(price.toString());
        tv_description.setText(description);
        new DownloadImageTask((ImageView) findViewById(R.id.bookdetail_img))
                .execute(imageUrl);

        //iv_image.setImageURI(Uri.parse("https://cdn.pixabay.com/photo/2016/09/01/10/23/image-1635747_960_720.jpg"));

    }
}
