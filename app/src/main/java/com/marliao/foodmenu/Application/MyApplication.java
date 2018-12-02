package com.marliao.foodmenu.Application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.marliao.foodmenu.db.doman.Comments;

public class MyApplication extends Application {

    private static Comments comments;

    public static Context context;
    public static Toast toast;

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
