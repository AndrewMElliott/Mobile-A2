package com.example.comp3074.a2100872220;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        db.execSQL("CREATE TABLE nurse (" +
                    "nurse_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "department TEXT NOT NULL," +
                    "password TEXT NOT NULL);");

        db.execSQL("CREATE TABLE patient (" +
                    "patient_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "doctor_id INTEGER," +
                    "room INTEGER," +
                    "FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id));");

        db.execSQL("CREATE TABLE doctor(" +
                    "doctor_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "first_name TEXT NOT NULL," +
                    "last_name TEXT NOT NULL," +
                    "department TEXT NOT NULL," +
                    "password TEXT NOT NULL);");

        db.execSQL("CREATE TABLE test(" +
                    "test_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "BP_systolic INTEGER," +
                    "BP_diastolic INTEGER," +
                    "temperature INTEGER," +
                    "blood_sugar INTEGER," +
                    "eye_test INTEGER," +
                    "FOREIGN KEY(patient_id) REFERENCES doctor(patient_id));");

        insertUser(db,"nurse", "nurse", "set", "admin", "nursing");
        insertUser(db,"doctor", "doctor", "sit", "admin", "MD");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP IF TABLE EXISTS nurse;");
        db.execSQL("DROP IF TABLE EXISTS doctor;");
        db.execSQL("DROP IF TABLE EXISTS patient;");
        db.execSQL("DROP IF TABLE EXISTS test;");
        onCreate(db);
    }

    public void insertUser(SQLiteDatabase db, String table, String firstName,String lastName,String pw, String department){
        ContentValues values = new ContentValues();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("password", pw);
        values.put("department", department);

        db.insert(table,null,values);


    }

    public Cursor confirmLogin(String firstName, String pw){

        String query = "SELECT first_name, password FROM nurse WHERE first_name ='" + firstName +
                "' AND password = '" + pw +"';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        return c;
    }

    public Map<Integer,String> getDoctorData(String table){
        Map<Integer,String> names = new HashMap<>();
        String query = "SELECT * FROM "+table+";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                names.put(c.getInt(0), c.getString(1));
            } while (c.moveToNext());
        }
        c.close();
        return names;
    }

    public void insertPatient(String firstName, String lastName, Integer room){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("room", room);


        db.insert("patient",null,values);
    }
    public Cursor getPatients(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM patient;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
