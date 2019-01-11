package com.android.adolphe.booksapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.adolphe.booksapp.Models.Book;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.android.adolphe.booksapp.Database.DatabaseContract.UserBooks;

public class CollectionDAO {

    private BooksDbHelper mDbHelper;
    private final Context context;

    public CollectionDAO(Context context) {
        this.context = context;
        mDbHelper = BooksDbHelper.getInstance(context);

    }

    public void addBook(String book_json) {
        SQLiteDatabase db = getmDbHelper().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserBooks.BOOK_OBJECT, book_json);
        db.insert(UserBooks.TABLE_NAME, null, values);
    }

    private BooksDbHelper getmDbHelper(){
        if(mDbHelper == null){
            mDbHelper = BooksDbHelper.getInstance(context);
        }
        return mDbHelper;
    }

    public ArrayList<Book> getCollection(){
        ArrayList<String> jsonBookList = new ArrayList<String>();
        ArrayList<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + UserBooks.TABLE_NAME ;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            while(c.moveToNext()){
                jsonBookList.add(c.getString(c.getColumnIndex(UserBooks.BOOK_OBJECT)));
            }
            c.close();
        }

        for(String book: jsonBookList){
            bookList.add(new Gson().fromJson(book, Book.class));
        }

        return bookList;
    }

    public boolean hasBook(String jsonBook) {
        ArrayList<String> jsonBookList = new ArrayList<String>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Book book = new Gson().fromJson(jsonBook, Book.class);
        String selectQuery = "SELECT * FROM " + UserBooks.TABLE_NAME ;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            while(c.moveToNext()){
                Book b = new Gson().fromJson(c.getString(c.getColumnIndex(UserBooks.BOOK_OBJECT)), Book.class);
                if(b.getVolumeInfo().getTitle().equals(book.getVolumeInfo().getTitle())
                        && b.getVolumeInfo().getAuthorsAsString().equals(book.getVolumeInfo().getAuthorsAsString())){
                    jsonBookList.add(c.getString(c.getColumnIndex(UserBooks.BOOK_OBJECT)));
                }
            }
            c.close();
        }
        return jsonBookList.size() > 0;
    }

    public boolean removeBook(String jsonBook) {
        SQLiteDatabase db = getmDbHelper().getWritableDatabase();
        ArrayList<String> jsonBookList = new ArrayList<>();
        Book book = new Gson().fromJson(jsonBook, Book.class);
        String selectQuery = "SELECT * FROM " + UserBooks.TABLE_NAME ;
        Cursor c = db.rawQuery(selectQuery, null);


        boolean returnValue = true;
        if (c != null) {
            while(c.moveToNext()){
                Book b = new Gson().fromJson(c.getString(c.getColumnIndex(UserBooks.BOOK_OBJECT)), Book.class);
                if(b.getVolumeInfo().getTitle().equals(book.getVolumeInfo().getTitle())
                        && b.getVolumeInfo().getAuthorsAsString().equals(book.getVolumeInfo().getAuthorsAsString())){
                    jsonBookList.add(c.getString(c.getColumnIndex(UserBooks.BOOK_OBJECT)));
                }
            }
            c.close();
            for(int i=0;i<jsonBookList.size();i++){
                returnValue =  db.delete(UserBooks.TABLE_NAME, UserBooks.BOOK_OBJECT + "='" + jsonBookList.get(i) +"'", null) > 0;
            }
        }
        return returnValue;
    }
}
