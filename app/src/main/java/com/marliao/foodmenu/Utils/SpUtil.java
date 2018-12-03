package com.marliao.foodmenu.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    private static SharedPreferences sp;

    /**
     * 向内存中存入变量
     * @param context
     * @param key 存储的key
     * @param value value 对应的boolean变量
     */
    public static void putBoolean(Context context ,String key ,boolean value){
        if(sp == null){
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 去数据库中取值
     * @param context
     * @param key 获取时的key值
     * @param value 取不到时的值
     * @return
     */
    public static boolean getBoolean(Context context, String key , boolean value){
        if(sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,value);
    }
}
