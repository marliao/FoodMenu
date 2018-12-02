package com.marliao.foodmenu.Application;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.marliao.foodmenu.db.doman.Comments;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Sort;

public class MyApplication extends Application {

    public static final String pathMenuTypes="http://192.168.1.101:8080/menu/types";
    public static final String pathMenuMenus="http://192.168.1.101:8080/menu/menus";
    public static final String pathMenuDetail="http://192.168.1.101:8080/menu/menuDetail";
    public static final String pathMenuSupport="http://192.168.1.101:8080/menu/support";
    public static final String pathMenuComments="http://192.168.1.101:8080/menu/comments";
    public static final String pathMenuPostComment="http://192.168.1.101:8080/menu/postComment";
    public static final String Http="http://192.168.1.101:8080/menu/";

    public static boolean like=false;
    public static boolean dislike=false;

    private static Comments comments;
    private static FoodMenu foodMenu;
    private static MenuDetail menuDetail;
    private static Sort sort;

    public static Context context;
    public static Toast toast;

    public static FoodMenu getFoodMenu() {
        return foodMenu;
    }

    public static void setFoodMenu(FoodMenu foodMenu) {
        MyApplication.foodMenu = foodMenu;
    }

    public static MenuDetail getMenuDetail() {
        return menuDetail;
    }

    public static void setMenuDetail(MenuDetail menuDetail) {
        MyApplication.menuDetail = menuDetail;
    }

    public static Sort getSort() {
        return sort;
    }

    public static void setSort(Sort sort) {
        MyApplication.sort = sort;
    }

    public static Comments getComments() {
        return comments;
    }

    public static void setComments(Comments comments) {
        MyApplication.comments = comments;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static Context getContext() {
        return context;
    }

    public static void showToast(String text) {
        toast.setText(text);
        toast.show();
    }
}
