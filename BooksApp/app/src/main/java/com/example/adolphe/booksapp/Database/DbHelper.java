package com.example.adolphe.booksapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "booksapp.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LOGTAG = "Users";
    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase db;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Connected to database.");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Diconnected from database.");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UsersDAO.UserEntry.TABLE_NAME + " (" +
                UsersDAO.UserEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UsersDAO.UserEntry.COL_FN + " TEXT NOT NULL, " +
                UsersDAO.UserEntry.COL_LN + " TEXT NOT NULL, " +
                UsersDAO.UserEntry.COL_EMAIL + " TEXT NOT NULL, " +
                UsersDAO.UserEntry.COL_UN + " TEXT NOT NULL " +
                UsersDAO.UserEntry.COL_PWD + " TEXT NOT NULL " +
                "); ";
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int oldVersion, int newVersion) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + UsersDAO.UserEntry.TABLE_NAME);
        onCreate(sqldb);
    }


}
