package com.ce.cechat.data.dao;

public class UserTable {

    public static final String TABLE_NAME = "user_table";

    public static final String NAME = "name";

    public static final String SHENFEN = "shenfen";

    public static final String DEPARTMENT = "department";

    public static final String INTRODUCTION = "introduction";

    public static final String HYPHENATE_ID = "hyphenate_id";

    public static final String NICKNAME = "nickname";

    public static final String HEAD = "head";

    public static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + HYPHENATE_ID + " TEXT PRIMARY KEY UNIQUE NOT NULL, "
            + NAME + " TEXT, "
            + NICKNAME + " TEXT, "
            + HEAD + " TEXT, "
            + SHENFEN + " TEXT, "
            + DEPARTMENT + " TEXT, "
            + INTRODUCTION + " TEXT);";

}
