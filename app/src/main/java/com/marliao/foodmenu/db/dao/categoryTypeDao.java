package com.marliao.foodmenu.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marliao.foodmenu.db.doman.Types;
import com.marliao.foodmenu.db.sqliteDatebase;

import java.util.ArrayList;
import java.util.List;

public class categoryTypeDao {
    private  sqliteDatebase sdb = null;
    public categoryTypeDao(Context context){
        sdb = new sqliteDatebase(context);
    }
    private static categoryTypeDao categorytype = null;
    public static categoryTypeDao getInstanceCategoryType(Context context){
       if(categorytype  == null){
           categorytype  = new categoryTypeDao(context);
       }
        return categorytype ;
    }
    public int insertType(Types type){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("typename",type.getTypename());
        values.put("typepic",type.getTypepic());
        values.put("description",type.getTypeid());
        long flag = db.insert("type", null, values);
        sdb.close();
        return (int) flag;
    };

    /**
     * 添加一个list集合到数据库
     * @param list
     */
    public void insertTypeList(List<Types> list){
        SQLiteDatabase db = sdb.getWritableDatabase();
        for(Types type : list){
            ContentValues values = new ContentValues();
            values.put("typename",type.getTypename());
            values.put("typepic",type.getTypepic());
            values.put("description",type.getTypeid());
            long flag = db.insert("type", null, values);
        }
        sdb.close();
    };
    public int deleteType(Integer id){
        SQLiteDatabase db = sdb.getWritableDatabase();
        long flag = db.delete("type","typeid=?",new String[]{String.valueOf(id)});
        sdb.close();
        return (int) flag;
    };
    public int updateType(Types type){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("typename",type.getTypename());
        values.put("typepic",type.getTypepic());
        values.put("description",type.getTypeid());
        long flag = db.update("type",
                values,"typeid=?",new String[]{String.valueOf(type.getTypeid())});
        sdb.close();
        return (int) flag;
    };
    public List<Types> findAll(){
        List<Types> list = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("type", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            if(list == null)list = new ArrayList<Types>();
            Types type = new Types();
            type.setTypeid(cursor.getInt(0));
            type.setTypename(cursor.getString(1));
            type.setTypepic(cursor.getString(2));
            type.setDescription(cursor.getString(3));
            list.add(type);
        }
        cursor.close();
        sdb.close();
        return list;
    }
    public Types findByID(Integer id){
        Types type = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("type", null, "typeid=?", new String[]{id+""}, null, null, null);
        if (cursor.moveToNext()){
            type = new Types();
            type.setTypeid(cursor.getInt(0));
            type.setTypename(cursor.getString(1));
            type.setTypepic(cursor.getString(2));
            type.setDescription(cursor.getString(3));
        }
        cursor.close();
        sdb.close();
        return type;
    }

}
