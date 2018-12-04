package com.marliao.foodmenu.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import static com.marliao.foodmenu.Application.MyApplication.context;

public class SaveDrawableUtil {
    public static void longPressClick(ImageView img, Context context, Integer position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("图片保存");
        builder.setMessage("是否保存图片");
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消对话框
            }
        });
        builder.show();
    }
}
