package com.android.adolphe.booksapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.adolphe.booksapp.Activities.MainActivity;
import com.android.adolphe.booksapp.Database.CollectionDAO;
import com.android.adolphe.booksapp.Models.Book;
import com.android.adolphe.booksapp.R;
import com.google.gson.Gson;

public class BookDetailFragment extends Fragment {

    private TextView tv_title, tv_description, tv_category, tv_author, tv_rating, tv_subtitle, tv_price;
    private ImageView iv_image;
    private Button btn_buy;
    private String jsonUser;
    private Book book;
    private View view;

    public BookDetailFragment() {
        //Bundle bundle = getArguments();
        //user = (User) bundle.get("user_json");
        //books = (ArrayList<Book>) bundle.get("wanted_book_json");
    }//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookdetail, container, false);
        tv_title = (TextView) view.findViewById(R.id.bookdetail_title);
        tv_author = (TextView) view.findViewById(R.id.bookdetail_author);
        tv_category = (TextView) view.findViewById(R.id.bookdetail_category);
        tv_description = (TextView) view.findViewById(R.id.bookdetail_desc);
        tv_rating = (TextView) view.findViewById(R.id.bookdetail_rating);
        tv_subtitle = (TextView) view.findViewById(R.id.bookdetail_subtitle);
        tv_price = (TextView) view.findViewById(R.id.bookdetail_price);
        iv_image = (ImageView) view.findViewById(R.id.bookdetail_img);
        btn_buy = (Button) view.findViewById(R.id.bookdetail_buyBtn);

        tv_title.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_subtitle.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_author.setShadowLayer(2, 3, 3, Color.BLACK);
        tv_description.setShadowLayer(3, 3, 3, Color.BLACK);
        btn_buy.setShadowLayer(10, 5, 5, Color.BLACK);

        String jsonBook = getArguments().getString("book_json");
        CollectionDAO dao = new CollectionDAO(getActivity().getApplicationContext());
        if(dao.hasBook(jsonBook)){
            btn_buy.setVisibility(View.INVISIBLE);
        }

        book = new Gson().fromJson(jsonBook, Book.class);

        if(book.getImageBitmap() != null){
            iv_image.setImageBitmap(book.getImageBitmap());
        } else {
            iv_image.setImageResource(R.drawable.nature);
            iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        if(book.getVolumeInfo().getTitle() != null)
            tv_title.setText(book.getVolumeInfo().getTitle());
        else  tv_title.setText(getString(R.string.untitledBook));

        if(book.getVolumeInfo().getSubtitle() != null)
            tv_subtitle.setText(book.getVolumeInfo().getSubtitle());
        else  tv_subtitle.setText("");

        if(book.getVolumeInfo().getAuthors() != null)
            tv_author.setText(book.getVolumeInfo().getAuthorsAsString());
        else tv_author.setText(getString(R.string.anonymousAuthor));

        if(book.getVolumeInfo().getPublishedDate() != null)
            tv_rating.setText(book.getVolumeInfo().getPublishedDate());
        else tv_rating.setText(getString(R.string.unknown));

        if(book.getVolumeInfo().getCategories() != null)
            tv_category.setText(book.getVolumeInfo().getCategories().get(0));
        else  tv_category.setText(getString(R.string.other));

        // Ik heb de prijs al gecontroleerd op nullpointers in de implementatie van Book.getPriceAsString()
        tv_price.setText(book.getPriceAsString());

        if(book.getVolumeInfo().getDescription() != null)
            tv_description.setText(book.getVolumeInfo().getDescription());
        else tv_description.setText(getString(R.string.mysteriousLookingBook));

        if(book.getSaleInfo().getRetailPrice() != null){
            tv_price.setText(book.getPriceAsString());
            btn_buy.setText(getString(R.string.buy_it));
        }
        else if(book.getSaleInfo().getListPrice() != null){
            tv_price.setText(book.getPriceAsString());
            btn_buy.setText(getString(R.string.buy_it));
        }
        else {
            tv_price.setText(R.string.free);
            btn_buy.setText(getString(R.string.take_it));
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_description.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
        }

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String jsonBook = new Gson().toJson(book);
            if(!new CollectionDAO(getActivity().getApplicationContext()).hasBook(jsonBook)){
                if(jsonBook != null && !jsonBook.isEmpty()){
                    CollectionDAO dao = new CollectionDAO(getActivity().getApplicationContext());
                    dao.addBook(jsonBook);
                    MainActivity.bookCollection = dao.getCollection();
                    ((MainActivity) getActivity()).changeToFragmentWithId(R.id.nav_collection);
                    Toast.makeText(getActivity(),R.string.AddToCollectionConfirmation, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    getActivity().getApplicationContext().startActivity(intent);
                }
            }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_book_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getArguments() != null){
                    if(!getArguments().getBoolean("fromCatalog")){
                        ((MainActivity) getActivity()).changeToFragmentWithId(R.id.nav_collection);
                        return;
                    }
                }
                ((MainActivity) getActivity()).fillAndCallCatalogFragment();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return view;
    }
}
