package com.example.adolphe.booksapp.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adolphe.booksapp.R;

import java.net.URL;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    public Context ctx;
    public List<Book> books;

    public CatalogAdapter(Context ctx, List<Book> books) {
        this.ctx = ctx;
        this.books = books;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater li = LayoutInflater.from(ctx);
        view = li.inflate(R.layout.book_cardview, viewGroup, false);
        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder catalogViewHolder, int i) {
        catalogViewHolder.tv_book_title.setText(books.get(i).getTitle());
        catalogViewHolder.tv_authors.setText(books.get(i).getAuthors());
        int colorId = (int) Math.floor(books.get(i).getRating());
        catalogViewHolder.tv_rating.setText(String.valueOf(books.get(i).getRating()));
        URL url = null;
        try {
            url = new URL(books.get(i).getImgUrl());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            catalogViewHolder.img_book_cover.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
            catalogViewHolder.img_book_cover.setImageResource(R.mipmap.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title, tv_authors, tv_rating;
        ImageView img_book_cover;

        public CatalogViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title);
            tv_authors = (TextView) itemView.findViewById(R.id.book_authors);
            tv_rating = (TextView) itemView.findViewById(R.id.book_rating);
            img_book_cover = (ImageView) itemView.findViewById(R.id.book_img);

        }
    }
}
