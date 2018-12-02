package com.marliao.foodmenu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqliteDatebase extends SQLiteOpenHelper {
    public sqliteDatebase(Context context) {
        super(context, "types.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table type(" +
                "typeid integer primary key autoincrement," +
                "typename varchar(20)," +
                "typepic varchar(20)," +
                "description varchar(40))");

        db.execSQL("create table menu(" +
                " menuid Integer primary key autoincrement," +
                " menuname varchar(30)," +
                " spic varchar(30)," +
                " assistmaterial varchar(30)," +
                " notlikes Integer," +
                " abstracts varchar(30)," +
                " mainmaterial varchar(30)," +
                " typeid Integer," +
                " likes Integer," +
                " foreign key(typeid) references type(typeid))");

        db.execSQL("create table step(" +
                " stepid Integer primary key autoincrement," +
                " description varchar(50)," +
                " menuid integer," +
                " pic varchar(30)," +
                " islike Integer," +
                " iscolleck Integer," +
                " foreign key(menuid) references menu(menuid))");

        db.execSQL("create table comments(" +
                "menuid Integer," +
                "region varchar(30)," +
                "content text," +
                " date varchar(30)," +
                " hours varchar(30)," +
                " seconds varchar(30)," +
                " month varchar(30)," +
                " nanos varchar(10)," +
                " timezoneOffset varchar(30)," +
                " year varchar(30)," +
                " minutes varchar(30)," +
                " time varchar(30)," +
                " day varchar(30)," +
                " foreign key(menuid) references menu(menuid))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
