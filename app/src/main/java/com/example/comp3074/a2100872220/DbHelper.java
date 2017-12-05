package com.example.comp3074.a2100872220;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Drew on 11/18/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "clinic";
    private static final int DATABASE_VERSION = 1;


    public DbHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
