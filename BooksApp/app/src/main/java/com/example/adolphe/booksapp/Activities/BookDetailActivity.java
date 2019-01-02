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

import com.example.adolphe.booksapp.R;
import com.jgabrielfreitas.core.BlurImageView;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_description, tv_category, tv_author, tv_rating, tv_subtitle, tv_price;
    private ImageView iv_image;
    private BlurImageView iv_blurry_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tv_title = findViewById(R.id.bookdetail_title);
        tv_author = findViewById(R.id.bookdetail_author);
        tv_category = findViewById(R.id.bookdetail_category);
        tv_description = findViewById(R.id.bookdetail_desc);
        tv_rating = findViewById(R.id.bookdetail_rating);
        tv_subtitle = findViewById(R.id.bookdetail_subtitle);
        tv_price = findViewById(R.id.bookdetail_price);
        iv_image = findViewById(R.id.bookdetail_img);
        iv_blurry_image = findViewById(R.id.bookdetail_blurImage);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String subtitle = intent.getExtras().getString("Subtitle");
        String author = intent.getExtras().getString("Author");
        String description = intent.getExtras().getString("Description");
        Double rating = intent.getExtras().getDouble("Rating");
        Double price = intent.getExtras().getDouble("Price");
        String imageUrl = intent.getExtras().getString("ImageUrl");
        String category = intent.getExtras().getString("Category");
        Bitmap imageBitmap = (Bitmap) intent.getParcelableExtra("ImageBitmap");
        tv_title.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_subtitle.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_author.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_description.setShadowLayer(3, 3, 3, Color.BLACK);

        tv_title.setTextColor(Color.WHITE);
        tv_subtitle.setTextColor(Color.WHITE);
        tv_author.setTextColor(Color.WHITE);
        tv_description.setTextColor(Color.WHITE);

        tv_title.setText(title);
        tv_subtitle.setText(subtitle);
        tv_author.setText(author);
        tv_rating.setText(rating.toString());
        tv_category.setText(category);
        tv_price.setText(price.toString());
        tv_description.setText(description);/*
        DownloadImageTask dl = new DownloadImageTask((ImageView) findViewById(R.id.bookdetail_img));
        try {
            dl.execute(imageUrl).wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap ibm = dl.getImageBitmap();

        if(dl.getImageBitmap() != null){
            iv_blurry_image.setImageBitmap(dl.getImageBitmap());
        } else {
            iv_blurry_image.setImageResource(R.mipmap.mainbackground);
            iv_blurry_image.setScaleY(10);
        }
*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_description.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
        }

        iv_image.setImageBitmap(imageBitmap);
        iv_blurry_image.setImageBitmap(imageBitmap);
        iv_blurry_image.setBlur(5);
        //iv_image.setImageURI(Uri.parse("https://cdn.pixabay.com/photo/2016/09/01/10/23/image-1635747_960_720.jpg"));

    }
}
