package com.marliao.foodmenu.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.sqliteDatebase;

import java.util.ArrayList;
import java.util.List;

public class menuDao {
    private  sqliteDatebase sdb = null;
    public menuDao(Context context){
        sdb = new sqliteDatebase(context);
    }
    private menuDao menuDao = null;
    public menuDao getInstanceMenu(Context context){
       if(menuDao  == null){
           menuDao  = new menuDao(context);
       }
        return menuDao;
    }
    public int insertMenu(Menu menu){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("menuname",menu.getMenuname());
        values.put("spic",menu.getSpic());
        values.put("assistmaterial",menu.getAssistmaterial());
        values.put("notlikes",menu.getNotlikes());
        values.put("abstracts",menu.getAbstracts());
        values.put("mainmaterial",menu.getMainmaterial());
        values.put("typeid",menu.getTypeid());
        values.put("likes",menu.getLikes());
        long flag = db.insert("menu", null, values);
        sdb.close();
        return (int) flag;
    };
    public int deleteMenu(Integer id){
        SQLiteDatabase db = sdb.getWritableDatabase();
        long flag = db.delete("menu","menuid=?",new String[]{String.valueOf(id)});
        sdb.close();
        return (int) flag;
    };
    public int updateMenu(Menu menu){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("menuname",menu.getMenuname());
        values.put("spic",menu.getSpic());
        values.put("assistmaterial",menu.getAssistmaterial());
        values.put("notlikes",menu.getNotlikes());
        values.put("abstracts",menu.getAbstracts());
        values.put("mainmaterial",menu.getMainmaterial());
        values.put("typeid",menu.getTypeid());
        values.put("likes",menu.getLikes());
        long flag = db.update("menu",
                values,"menuid=?",new String[]{String.valueOf(menu.getTypeid())});
        sdb.close();
        return (int) flag;
    };
    public List<Menu> findAll(){
        List<Menu> list = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("menu", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            if(list == null)list = new ArrayList<Menu>();
            Menu menu = new Menu();
            menu.setMenuid(cursor.getInt(0));
            menu.setMenuname(cursor.getString(1));
            menu.setSpic(cursor.getString(2));
            menu.setAssistmaterial(cursor.getString(3));
            menu.setLikes(cursor.getInt(4));
            menu.setAbstracts(cursor.getString(5));
            menu.setMainmaterial(cursor.getString(6));
            menu.setTypeid(cursor.getInt(7));
            menu.setLikes(cursor.getInt(8));
            list.add(menu);
        }
        cursor.close();
        sdb.close();
        return list;
    }
    public Menu findByID(Integer id){
        Menu menu = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("menu", null, "menuid=?", new String[]{id+""}, null, null, null);
        if (cursor.moveToNext()){
            menu = new Menu();
            menu.setMenuid(cursor.getInt(0));
            menu.setMenuname(cursor.getString(1));
            menu.setSpic(cursor.getString(2));
            menu.setAssistmaterial(cursor.getString(3));
            menu.setLikes(cursor.getInt(4));
            menu.setAbstracts(cursor.getString(5));
            menu.setMainmaterial(cursor.getString(6));
            menu.setTypeid(cursor.getInt(7));
            menu.setLikes(cursor.getInt(8));
        }
        cursor.close();
        sdb.close();
        return menu;
    }

}