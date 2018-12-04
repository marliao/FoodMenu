package com.marliao.foodmenu.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marliao.foodmenu.db.doman.Comment;
import com.marliao.foodmenu.db.doman.Ptime;
import com.marliao.foodmenu.db.sqliteDatebase;

import java.util.ArrayList;
import java.util.List;

public class commentsDao {
    private  sqliteDatebase sdb = null;
    public commentsDao(Context context){
        sdb = new sqliteDatebase(context);
    }
    private static commentsDao commentsDao = null;
    public static commentsDao getInstanceComments(Context context){
       if(commentsDao  == null){
           commentsDao  = new commentsDao(context);
       }
        return commentsDao;
    }
    public int insertComment(Comment comment){
        SQLiteDatabase db = sdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("menuid",comment.getMenuid());
        values.put("region",comment.getRegion());
        values.put("content",comment.getContent());
        values.put("date",comment.getPtime().getDate());
        values.put("hours",comment.getPtime().getHours());
        values.put("seconds",comment.getPtime().getSeconds());
        values.put("month",comment.getPtime().getMonth());
        values.put("nanos",comment.getPtime().getNanos());
        values.put("timezoneOffset",comment.getPtime().getTimezoneOffset());
        values.put("year",comment.getPtime().getYear());
        values.put("minutes",comment.getPtime().getMinutes());
        values.put("time",comment.getPtime().getTime());
        values.put("day",comment.getPtime().getDay());
        long flag = db.insert("comments", null, values);
        sdb.close();
        return (int) flag;
    };

    /**
     * 添加一个list集合进入到数据库
     * @param list
     */
    public void insertCommentList(List<Comment> list){
        SQLiteDatabase db = sdb.getWritableDatabase();
        for(Comment comment:list){
            ContentValues values = new ContentValues();
            values.put("menuid",comment.getMenuid());
            values.put("region",comment.getRegion());
            values.put("content",comment.getContent());
            values.put("date",comment.getPtime().getDate());
            values.put("hours",comment.getPtime().getHours());
            values.put("seconds",comment.getPtime().getSeconds());
            values.put("month",comment.getPtime().getMonth());
            values.put("nanos",comment.getPtime().getNanos());
            values.put("timezoneOffset",comment.getPtime().getTimezoneOffset());
            values.put("year",comment.getPtime().getYear());
            values.put("minutes",comment.getPtime().getMinutes());
            values.put("time",comment.getPtime().getTime());
            values.put("day",comment.getPtime().getDay());
            long flag = db.insert("comments", null, values);
        }
        sdb.close();
    };

    /**
     * 删除某人所有的评论
     * @param menuid 菜单的id
     * @param nanos 人的名字
     * @return
     */
    public int deleteComments(String menuid,String nanos){
        SQLiteDatabase db = sdb.getWritableDatabase();
        long flag = db.delete("comments","menuid=? and nanos=?",new String[]{menuid,nanos});
        sdb.close();
        return (int) flag;
    };

    public List<Comment> findAll(Integer menuid){
        List<Comment> list = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("comments", null, "menuid=?", new String[]{menuid+""}, null, null, null);
        while(cursor.moveToNext()){
            if(list == null)list = new ArrayList<Comment>();
            Comment comment = new Comment();
            comment.setMenuid(cursor.getInt(0));
            comment.setRegion(cursor.getString(1));
            comment.setContent(cursor.getString(2));
            Ptime ptime = new Ptime();
            ptime.setDate(cursor.getString(3));
            ptime.setHours(cursor.getString(4));
            ptime.setSeconds(cursor.getString(5));
            ptime.setMonth(cursor.getString(6));
            ptime.setNanos(cursor.getString(7));
            ptime.setTimezoneOffset(cursor.getString(8));
            ptime.setYear(cursor.getString(9));
            ptime.setMinutes(cursor.getString(10));
            ptime.setTime(cursor.getString(11));
            ptime.setDay(cursor.getString(12));
            comment.setPtime(ptime);
            list.add(comment);
        }
        cursor.close();
        sdb.close();
        return list;
    }

    /**
     * 根据人的名字查询他的所有的评论
     * @param nanos 代表人名
     * @return
     */
    public Comment findByNanos(String nanos){
        Comment comment = null;
        SQLiteDatabase db = sdb.getWritableDatabase();
        Cursor cursor = db.query("comments", null, "nanos=?", new String[]{nanos}, null, null, null);
        if (cursor.moveToNext()){
             comment = new Comment();
            comment.setMenuid(cursor.getInt(0));
            comment.setRegion(cursor.getString(1));
            comment.setContent(cursor.getString(2));
            Ptime ptime = new Ptime();
            ptime.setDate(cursor.getString(3));
            ptime.setHours(cursor.getString(4));
            ptime.setSeconds(cursor.getString(5));
            ptime.setMonth(cursor.getString(6));
            ptime.setNanos(cursor.getString(7));
            ptime.setTimezoneOffset(cursor.getString(8));
            ptime.setYear(cursor.getString(9));
            ptime.setMinutes(cursor.getString(10));
            ptime.setTime(cursor.getString(11));
            ptime.setDay(cursor.getString(12));
            comment.setPtime(ptime);
        }
        cursor.close();
        sdb.close();
        return comment;
    }
    public int deleteCommentsAll(){
        SQLiteDatabase db = sdb.getWritableDatabase();
        long flag = db.delete("comments",null,null);
        sdb.close();
        return (int) flag;
    };

}
