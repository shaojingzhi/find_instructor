package com.ce.cechat.data.dao;


public class ContactTable {

    public static final String TABLE_NAME = "contact_table";

    public static final String ID = "id";

    public static final String HYPHENATE_ID = "hyphenate_id";

    public static final String NAME = "name";

    public static final String SHENFEN = "shenfen";

    public static final String DEPARTMENT = "department";

    public static final String INTRODUCTION = "introduction";

    public static final String NICKNAME = "nickname";

    public static final String HEAD = "head";

    //1 表示是 0表示不是
    public static final String IS_CONTACT = "is_contact";


    public static final String TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, "
            + HYPHENATE_ID + " TEXT NOT NULL UNIQUE, "
            + NAME + " TEXT, "
            + NICKNAME + " TEXT, "
            + HEAD + " TEXT, "
            + SHENFEN + " TEXT, "
            + DEPARTMENT + " TEXT, "
            + INTRODUCTION + " TEXT, "
            + IS_CONTACT + " INTEGER DEFAULT (1));";

}
