package com.example.adolphe.booksapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.adolphe.booksapp.Model.PojoBook;
import com.example.adolphe.booksapp.R;
import com.google.gson.Gson;
import com.jgabrielfreitas.core.BlurImageView;
import com.yinglan.shadowimageview.ShadowImageView;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_description, tv_category, tv_author, tv_rating, tv_subtitle, tv_price;
    private ShadowImageView iv_image;
    private BlurImageView iv_blurry_image;
    private Button btn_buy;

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
        btn_buy = (Button) findViewById(R.id.bookdetail_buyBtn);

        Intent intent = getIntent();
        String jsonBook = intent.getExtras().getString("PojoBook");
        PojoBook book = new Gson().fromJson(jsonBook, PojoBook.class);
        tv_title.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_subtitle.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_author.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_description.setShadowLayer(3, 3, 3, Color.BLACK);
        btn_buy.setShadowLayer(10, 5, 5, Color.BLACK);



        if(book.getImageBitmap() != null){
            iv_blurry_image.setImageBitmap(book.getImageBitmap());
            iv_image.setImageBitmap(book.getImageBitmap());
            iv_image.setScaleX(2.5F);
            iv_image.setScaleY(2.5F);

        } else {
            iv_blurry_image.setImageResource(R.mipmap.placeholder);
            iv_image.setImageResource(R.mipmap.placeholder);
        }

        try {
            iv_image.setImageShadowColor(Color.BLACK);
            iv_blurry_image.setBlur(5);
            iv_blurry_image.setScaleX(2);
            iv_blurry_image.setScaleY(2.5F);
        } catch (RuntimeException r) {
            r.printStackTrace();
        } finally {

        }

        if(book.getVolumeInfo().getTitle() != null)
            tv_title.setText(book.getVolumeInfo().getTitle());
        else  tv_title.setText("Untitled Book");

        if(book.getVolumeInfo().getSubtitle() != null)
            tv_subtitle.setText(book.getVolumeInfo().getSubtitle());
        else  tv_subtitle.setText("");

        if(book.getVolumeInfo().getAuthors() != null)
            tv_author.setText(book.getVolumeInfo().getAuthorsAsString());
        else tv_author.setText("Anonymous Author");

        if(book.getVolumeInfo().getPublishedDate() != null)
            tv_rating.setText(book.getVolumeInfo().getPublishedDate());
        else tv_rating.setText("Unknown");

        if(book.getVolumeInfo().getCategories() != null)
            tv_category.setText(book.getVolumeInfo().getCategories().get(0));
        else  tv_category.setText("Other");

        // Ik heb de prijs al gecontroleerd op nullpointers in de implementatie van Pojobook.getPriceAsString()
        tv_price.setText(book.getPriceAsString());

        if(book.getVolumeInfo().getDescription() != null)
            tv_description.setText(book.getVolumeInfo().getDescription());
        else tv_description.setText("A mysterious looking book.");

        btn_buy.setText(tv_price.getText().equals("FREE")? "Take it!": "Buy it!");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_description.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
        }
    }
}
