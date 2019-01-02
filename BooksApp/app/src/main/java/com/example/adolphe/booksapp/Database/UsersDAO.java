package com.example.adolphe.booksapp.Database;

import android.provider.BaseColumns;

public class UsersDAO {

    public static final class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "Users";
        public static final String COL_ID = "id";
        public static final String COL_FN = "firstName";
        public static final String COL_LN = "lastName";
        public static final String COL_EMAIL = "email";
        public static final String COL_UN = "username";
        public static final String COL_PWD = "password";
    }

}
