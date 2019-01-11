package com.android.adolphe.booksapp.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.adolphe.booksapp.Activities.MainActivity;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.Models.DownloadImageTask;
import com.android.adolphe.booksapp.R;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {
    Context ctx;
    ArrayList<Book> books;
    CatalogViewHolder viewHolder;
    private static Fragment fragment;
    int index;

    public CatalogAdapter(Context ctx, ArrayList<Book> books) {
        this.ctx = ctx;
        this.books = books;
    }

    public CatalogAdapter(Fragment fragment, Context ctx, ArrayList<Book> books) {
        this.ctx = ctx;
        this.books = books;
        CatalogAdapter.fragment = fragment;
    }

    public CatalogViewHolder getViewholder(){ return viewHolder; }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater li = LayoutInflater.from(ctx);
        view = li.inflate(R.layout.book_cardview, viewGroup, false);
        viewHolder = new CatalogViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder catalogViewHolder, final int i) {
        index = i;
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
            catalogViewHolder.tv_rating.setText(R.string.free);
        };

        // Als object al een imageBitmap heeft gaan we die niet meer downloaden maar zetten we die meteen op de view
        // Hierdoor winnen we extreem veel performantie omdat er veel images zijn die 1 voor 1 moeten gedownload worden
        // Wanneer het toestel in/uit Landscapt of portrait mode wordt gezet/gehaald zal de activity verplicht een refresh nodig hebben,
        // Daar valt helaas niet veel aan te doen
        if(books.get(i).getVolumeInfo().getImageLinks() != null){
            if(books.get(i).getImageBitmap() == null){
                String imageUrl = "https://books.google.com/books/content?id="+ books.get(i).getId() +"&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api";
                new DownloadImageTask(catalogViewHolder.img_book_cover, books.get(i)).execute(imageUrl);
            } else {
                catalogViewHolder.img_book_cover.setImageBitmap(books.get(i).getImageBitmap());
            }
        } else {
            catalogViewHolder.img_book_cover.setImageResource(R.drawable.nature);
            catalogViewHolder.img_book_cover.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        catalogViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment != null){
                    ((MainActivity) fragment.getActivity()).onBookDetailsRequest(books.get(i), true);
                } else {
                    ((MainActivity) ctx).onBookDetailsRequest(books.get(i), true);
                }
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
        ConstraintLayout cardView;

        public ConstraintLayout getCardView() {
            return cardView;
        }

        public CatalogViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title);
            tv_authors = (TextView) itemView.findViewById(R.id.book_authors);
            tv_rating = (TextView) itemView.findViewById(R.id.book_rating);
            img_book_cover = (ImageView) itemView.findViewById(R.id.book_img);
            cardView = (ConstraintLayout) itemView.findViewById(R.id.bookcard);
            tv_no_books = (TextView) itemView.findViewById(R.id.no_books_msg);
        }
    }
}
