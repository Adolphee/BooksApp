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

// Source: StackOverflow
// Code werd aangepast om te voldoen aan mijn eisen

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Book book;

    public DownloadImageTask(ImageView bmImage, Book book) {
        this.bmImage = bmImage;
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
        return downloadedImage;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            bmImage.setImageBitmap(result);
            book.setImageBitmap(result.getHeight() > 100 && result.getWidth() > 100? result: null);
        }
    }
}