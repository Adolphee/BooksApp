package com.android.adolphe.booksapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.adolphe.booksapp.Activities.MainActivity;
import com.android.adolphe.booksapp.Fragments.CollectionFragment;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.Models.DownloadImageTask;
import com.android.adolphe.booksapp.R;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {
    Context ctx;
    ArrayList<Book> books;
    CollectionAdapter.CollectionViewHolder viewHolder;
    private static CollectionFragment fragment;
    int index;
    boolean imagesHaveBeenDownloaded = false;

    public CollectionAdapter(Context ctx, ArrayList<Book> books) {
        this.ctx = ctx;
        this.books = books;
    }

    public CollectionAdapter(CollectionFragment fragment, Context ctx, ArrayList<Book> books) {
        this.ctx = ctx;
        this.books = books;
        CollectionAdapter.fragment = fragment;
    }

    public CollectionAdapter.CollectionViewHolder getViewholder(){ return viewHolder; }

    @NonNull
    @Override
    public CollectionAdapter.CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater li = LayoutInflater.from(ctx);
        view = li.inflate(R.layout.book_cardview, viewGroup, false);
        viewHolder = new CollectionAdapter.CollectionViewHolder(view);
        imagesHaveBeenDownloaded = true;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.CollectionViewHolder catalogViewHolder, final int i) {
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
                if(imagesHaveBeenDownloaded){
                    String imageUrl = "https://books.google.com/books/content?id="+ books.get(i).getId() +"&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api";
                    new DownloadImageTask(catalogViewHolder.img_book_cover, books.get(i)).execute(imageUrl);
                } else {
                    catalogViewHolder.img_book_cover.setImageBitmap(books.get(i).getImageBitmap());
                }
        }

        catalogViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment != null){
                    ((MainActivity) fragment.getActivity()).onBookDetailsRequest(books.get(i), false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(books != null) return books.size();
        return 0;
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title, tv_authors, tv_rating, tv_no_books;
        ImageView img_book_cover;
        ConstraintLayout cardView;

        public ConstraintLayout getCardView() {
            return cardView;
        }

        public CollectionViewHolder(View itemView) {
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
