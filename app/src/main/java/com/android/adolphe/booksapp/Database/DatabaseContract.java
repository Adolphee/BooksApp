package com.android.adolphe.booksapp.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract()
    {
    }
/*
    static class Users implements BaseColumns
    {
        static final String TABLE_NAME = "Users";
        static final String FIRSTNAME = "firstname";
        static final String LASTNAME = "lastname";
        static final String EMAIL = "email";
        static final String PASSWORD = "password";
        private Users () {}
    }
*/
    static class UserBooks implements BaseColumns
    {
        static final String TABLE_NAME = "UserBooks";
        static final String BOOK_OBJECT = "book_json";
        private UserBooks () {}
    }
}
