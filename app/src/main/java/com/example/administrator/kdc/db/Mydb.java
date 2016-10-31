package com.example.administrator.kdc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/27.
 */
public class Mydb extends SQLiteOpenHelper {

    public static final String CREATE_USER_TBL = "create table user_tbl ("
            + "id integer primary key ,"
            + "user_number integer,"
            + "user_pwd text,"
            + "user_emal text)";

    public static final String CREATE_USER_KEY_TBL = "create table user_key_tbl ("
            + "id integer primary key ,"
            + "user_id integer,"
            + "user_key text)";

    public static final String CREATE_USERSHOW_TBL = "create table usershow_tbl ("
            + "id integer primary key ,"
            + "user_id integer,"
            + "usershow_name text,"
            + "usershow_sex text,"
            + "usershow_age integer,"
            + "usershow_birthday text,"
            + "usershow_head text,"
            + "usershow_credit text,"
            + "usershow_money integer,"
            + "address_id integer)";


    private Context mContext;

    public Mydb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TBL);
        db.execSQL(CREATE_USERSHOW_TBL);
        db.execSQL(CREATE_USER_KEY_TBL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}