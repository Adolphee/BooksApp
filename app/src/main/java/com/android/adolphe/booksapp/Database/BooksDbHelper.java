package com.android.adolphe.booksapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.android.adolphe.booksapp.Database.DatabaseContract.UserBooks;
//import static com.android.adolphe.booksapp.Database.DatabaseContract.Users;

public class BooksDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Collections2.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TYPE_TEXT = " TEXT";
/*
    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + Users.TABLE_NAME + " (" +
                    Users.EMAIL + TYPE_TEXT + " PRIMARY KEY," +
                    Users.FIRSTNAME + TYPE_TEXT + "," +
                    Users.LASTNAME + TYPE_TEXT +
                    Users.EMAIL + TYPE_TEXT + " UNIQUE,"+
                    Users.PASSWORD + TYPE_TEXT +")";
*/
    private static final String SQL_CREATE_USERBOOKS =
            "CREATE TABLE " + UserBooks.TABLE_NAME + " (" +
                    UserBooks.BOOK_OBJECT + TYPE_TEXT + " PRIMARY KEY)";
/*
    private static final String SQL_DELETE_ARTIST =
            "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
            */
    private static final String SQL_DELETE_ALBUM =
            "DROP TABLE IF EXISTS " + UserBooks.TABLE_NAME;

    private static BooksDbHelper sInstance;

    private BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized BooksDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BooksDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_USERBOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALBUM);
        //db.execSQL(SQL_DELETE_ARTIST);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
