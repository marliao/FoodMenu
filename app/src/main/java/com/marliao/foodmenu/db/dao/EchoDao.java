package com.marliao.foodmenu.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marliao.foodmenu.db.doman.Echo;
import com.marliao.foodmenu.db.sqliteDatebase;

import java.util.ArrayList;
import java.util.List;

public class EchoDao {
    private sqliteDatebase sdb = null;
    private EchoDao(Context context){
        sdb = new sqliteDatebase(context);
    }
    private static EchoDao menuDao = null;
    public static EchoDao getInstanceMenuDetail(Context context){
        if(menuDao  == null){
            menuDao  = new EchoDao(context);
        }
        return menuDao;
    }

    public int insertMenuid(Integer menuid){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("menuid",menuid);
        value.put("isLike",0);
        value.put("isNotLike",0);
        value.put("isColleck",0);
        long rs = db.insert("echo", null, value);
        db.close();
        return (int) rs;
    }

    public int findMenuidLike(Integer menuid){
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("echo", new String[]{"isLike"}, "menuid=?", new String[]{menuid + ""}, null, null, null);
        if(cursor.moveToNext()){
            return cursor.getInt(0);
        }
        db.close();
        cursor.close();
        return 0;
    }
    public int updateMenuidLike(Integer menuid ,Integer isLike){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("isLike",isLike);
        int rs = db.update("echo", value, "menuid=?", new String[]{menuid + ""});
        db.close();
        return rs;
    }
    public Echo findById(Integer menuid){
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("echo", null, "menuid=?", new String[]{menuid + ""}, null, null, null);
        Echo echo = null;
        if(cursor.moveToNext()){
            echo = new Echo(cursor.getInt(0),cursor.getInt(1),
                    cursor.getInt(2),cursor.getInt(3));
        }
        db.close();
        cursor.close();
        return echo;
    }

    public int findMenuidNotLike(Integer menuid){
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("echo", new String[]{"isNotLike"}, "menuid=?", new String[]{menuid + ""}, null, null, null);
        if(cursor.moveToNext()){
            return cursor.getInt(0);
        }
        db.close();
        cursor.close();
        return 0;
    }
    public int updateMenuidNotLike(Integer menuid,Integer isNotLike){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("isNotLike",isNotLike);
        int rs = db.update("echo", value, "menuid=?", new String[]{menuid + ""});
        db.close();
        return rs;
    }
    public int findMenuidColleck(Integer menuid){
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("echo", new String[]{"isColleck"}, "menuid=?", new String[]{menuid + ""}, null, null, null);
        if(cursor.moveToNext()){
            return cursor.getInt(0);
        }
        db.close();
        cursor.close();
        return 0;
    }
    public int updateMenuidColleck(Integer menuid,Integer IsColleck){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("IsColleck",IsColleck);
        int rs = db.update("echo", value, "menuid=?", new String[]{menuid + ""});
        db.close();
        return rs;
    }

    public List<Echo> findAll(){
        List<Echo> list = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("echo", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            if(list == null) list = new ArrayList<Echo>();
            Echo echo = new Echo(cursor.getInt(0),cursor.getInt(1),
                    cursor.getInt(2),cursor.getInt(3));
            list.add(echo);
        }
        db.close();
        cursor.close();
        return list;
    }
}
