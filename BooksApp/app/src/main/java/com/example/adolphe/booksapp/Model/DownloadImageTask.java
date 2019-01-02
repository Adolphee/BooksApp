package com.example.adolphe.booksapp.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/*
* Met deze klasse haal ik een image van het internet binnen
* voordat ik die kan weergeven in de bookdetail activity
* */

// Source: https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android

// Code werd aangepast om te voldoen aan mijn eisen

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Bitmap imageBitmap;
    private Book book;

    public DownloadImageTask(ImageView bmImage, Book book) {
        this.bmImage = bmImage;
        this.book = book;
    }

    public Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        book.setImageBitmap(result);
    }
}