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
    private static  stepDao stepDao = null;
    public static stepDao getInstanceStep(Context context){
       if(stepDao  == null){
           stepDao  = new stepDao(context);
       }
        return stepDao;
    }

    /**
     * 添加一个step
     * @param step
     * @return 影响的行数
     */
    public int insertStep(Steps step){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description",step.getDescription());
        values.put("menuid",step.getMenuid());
        values.put("pic",step.getPic());
        values.put("islike",0);
        values.put("iscolleck",0);
        long flag = db.insert("step", null, values);
        sdb.close();
        return (int) flag;
    };

    /**
     * 添加一个list集合的steps
     * @param list
     */
    public void insertStepList(List<Steps> list){
        SQLiteDatabase db = sdb.getWritableDatabase();
        for(Steps step: list){
            ContentValues values = new ContentValues();
            values.put("description",step.getDescription());
            values.put("menuid",step.getMenuid());
            values.put("pic",step.getPic());
            values.put("islike",0);
            values.put("iscolleck",0);
            long flag = db.insert("step", null, values);
        }
        sdb.close();
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
            steps.setIslike(cursor.getInt(4));
            steps.setIscolleck(cursor.getInt(5));
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
            steps.setIslike(cursor.getInt(4));
            steps.setIscolleck(cursor.getInt(5));
        }
        cursor.close();
        sdb.close();
        return steps;
    }

    /**
     * 修改成是否喜欢 1代表喜欢 2代表不喜欢 0代表默认
     * @param stepId step的id值
     * @param isLike islike 代表是否喜欢的int型数
     * @return
     */
    public boolean updateIslike(Integer stepId  ,Integer isLike){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("islike",isLike);
        long flag = db.update("step",
                values,"stepid=?",new String[]{String.valueOf(stepId)});
        sdb.close();
        if(flag >0){
            return true;
        }
        return false;
    }

    /**
     *  根据id 查询是否喜欢（用作回显）
     *  1代表喜欢 2代表不喜欢 0代表默认
     * @param stepId
     * @return 返回代表喜欢的int 型值
     */
    public int selectIslike(Integer stepId){
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("step", new String[]{"islike"}, "stepid=?", new String[]{stepId + ""},
                null, null, null);
        if(cursor.moveToNext()){
            return cursor.getInt(0);
        }
        return 0;
    }
    /**
     * 修改成是否收藏 0代表不收藏 1代表收藏
     * @param stepId step的id值
     * @param isColleck iscolleck 代表是否喜欢的int型数
     * @return
     */
    public boolean updateIsColleck(Integer stepId  ,Integer isColleck){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("iscolleck",isColleck);
        long flag = db.update("step",
                values,"stepid=?",new String[]{String.valueOf(stepId)});
        sdb.close();
        if(flag >0){
            return true;
        }
        return false;
    }

    /**
     *  根据id 查询是否收藏（用作回显）
     *  0代表不收藏 1代表收藏
     * @param stepId
     * @return 返回代表喜欢的int 型值
     */
    public int selectIsColleck(Integer stepId){
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("step", new String[]{"iscolleck"}, "stepid=?", new String[]{stepId + ""},
                null, null, null);
        if(cursor.moveToNext()){
            return cursor.getInt(0);
        }
        return 0;
    }
}
