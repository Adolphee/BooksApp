package com.example.adolphe.booksapp.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.jgabrielfreitas.core.BlurImageView;
import com.yinglan.shadowimageview.ShadowImageView;

import java.io.InputStream;

/*
* Met deze klasse haal ik een image van het internet binnen
* voordat ik die kan weergeven in de bookdetail activity
* */

// Source: https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android

// Code werd aangepast om te voldoen aan mijn eisen

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private ShadowImageView shadowImageView;
    private BlurImageView blurImageView;
    private Bitmap imageBitmap;
    private Book book;

    public DownloadImageTask(ImageView bmImage, Book book) {
        this.bmImage = bmImage;
        this.book = book;
    }

    public DownloadImageTask(ShadowImageView bmImage, BlurImageView bi_img_view, Book book) {
        this.shadowImageView = bmImage;
        blurImageView = bi_img_view;
        this.book = book;
    }

    public Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap downloadedImage = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            downloadedImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        imageBitmap = downloadedImage;
        return downloadedImage;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if(shadowImageView != null){
            shadowImageView.setImageBitmap(result);
            shadowImageView.setImageShadowColor(Color.BLACK);
            shadowImageView.setScaleX(1.75F);
            shadowImageView.setScaleY(1.75F);
            blurImageView.setImageBitmap(result);
            blurImageView.setBlur(5);
            blurImageView.setScaleY(2);
        } else if (bmImage != null){
            bmImage.setImageBitmap(result);
        }
        book.setImageBitmap(result);
    }
}