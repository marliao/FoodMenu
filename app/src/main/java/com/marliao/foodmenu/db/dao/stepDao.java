package com.marliao.foodmenu.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marliao.foodmenu.db.doman.Steps;
import com.marliao.foodmenu.db.doman.Types;
import com.marliao.foodmenu.db.sqliteDatebase;

import java.util.ArrayList;
import java.util.List;

public class stepDao {
    private  sqliteDatebase sdb = null;
    public stepDao(Context context){
        sdb = new sqliteDatebase(context);
    }
    private stepDao stepDao = null;
    public stepDao getInstanceStep(Context context){
       if(stepDao  == null){
           stepDao  = new stepDao(context);
       }
        return stepDao;
    }
    public int insertStep(Steps step){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description",step.getDescription());
        values.put("menuid",step.getMenuid());
        values.put("pic",step.getPic());
        long flag = db.insert("step", null, values);
        sdb.close();
        return (int) flag;
    };
    public int deleteStep(Integer id){
        SQLiteDatabase db = sdb.getWritableDatabase();
        long flag = db.delete("step","stepid=?",new String[]{String.valueOf(id)});
        sdb.close();
        return (int) flag;
    };
    public int updateStep(Steps step){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description",step.getDescription());
        values.put("menuid",step.getMenuid());
        values.put("pic",step.getPic());
        long flag = db.update("step",
                values,"stepid=?",new String[]{String.valueOf(step.getStepid())});
        sdb.close();
        return (int) flag;
    };
    public List<Steps> findAll(){
        List<Steps> list = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("step", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            if(list == null)list = new ArrayList<Steps>();
            Steps steps = new Steps();
            steps.setStepid(cursor.getInt(0));
            steps.setDescription(cursor.getString(1));
            steps.setMenuid(cursor.getInt(2));
            steps.setPic(cursor.getString(3));
            list.add(steps);
        }
        cursor.close();
        sdb.close();
        return list;
    }
    public Steps findByID(Integer id){
        Steps steps = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("step", null, "stepid=?", new String[]{id+""}, null, null, null);
        if (cursor.moveToNext()){
            steps  = new Steps();
            steps.setStepid(cursor.getInt(0));
            steps.setDescription(cursor.getString(1));
            steps.setMenuid(cursor.getInt(2));
            steps.setPic(cursor.getString(3));
        }
        cursor.close();
        sdb.close();
        return steps;
    }

}
