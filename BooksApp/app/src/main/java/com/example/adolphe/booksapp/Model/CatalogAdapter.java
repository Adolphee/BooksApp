package com.example.adolphe.booksapp.Model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.adolphe.booksapp.Activities.BookDetailActivity;
import com.example.adolphe.booksapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    public Context ctx;
    public ArrayList<Book> books;

    public CatalogAdapter(Context ctx, ArrayList<Book> books) {
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
    public void onBindViewHolder(@NonNull CatalogViewHolder catalogViewHolder, final int i) {
        if(books.get(i).getVolumeInfo().getTitle() != null)
            catalogViewHolder.tv_book_title.setText(books.get(i).getVolumeInfo().getTitle());

        if(books.get(i).getVolumeInfo().getAuthorsAsString() != null)
            catalogViewHolder.tv_authors.setText(books.get(i).getVolumeInfo().getAuthorsAsString());

        // Kan beter... maar ik heb geen tijd
        if(books.get(i).getSaleInfo().getRetailPrice() != null)
            catalogViewHolder.tv_rating.setText(books.get(i).getSaleInfo().getRetailPrice().getAmount().toString());
         else if(books.get(i).getSaleInfo().getListPrice() != null)
            catalogViewHolder.tv_rating.setText(books.get(i).getSaleInfo().getListPrice().getAmount().toString());
         else {
            catalogViewHolder.tv_rating.setText("FREE");
        };

        if(books.get(i).getVolumeInfo().getImageLinks() != null){
            String imageUrl = "https://books.google.com/books/content?id="+ books.get(i).getId() +"&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api";
            new DownloadImageTask(catalogViewHolder.img_book_cover, books.get(i)).execute(imageUrl);
        }

        catalogViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, BookDetailActivity.class);
                intent.putExtra("Book", new Gson().toJson(books.get(i)));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(books != null) return books.size();
        return 0;
    }

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title, tv_authors, tv_rating, tv_no_books;
        ImageView img_book_cover;
        CardView cardView;

        public CatalogViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title);
            tv_authors = (TextView) itemView.findViewById(R.id.book_authors);
            tv_rating = (TextView) itemView.findViewById(R.id.book_rating);
            img_book_cover = (ImageView) itemView.findViewById(R.id.book_img);
            cardView = (CardView) itemView.findViewById(R.id.bookcard);
            tv_no_books = (TextView) itemView.findViewById(R.id.no_books_msg);
        }
    }
}
