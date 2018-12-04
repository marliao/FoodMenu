package com.marliao.foodmenu.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.marliao.foodmenu.Application.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class SaveDrawableUtil {

    /**
     * 弹出是否保存对话框
     * @param str
     * @param context
     */
    public static void longPressClick(final String str, final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("图片保存");
        builder.setMessage("是否保存图片");
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownPhotoUtil.donwloadImg(context,str);
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

    public  static void putDrawable(final Context context, String path, final String fileName){
        final String imgurl = MyApplication.Http + path;
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(imgurl);
                    InputStream stream = url.openStream();
                    File filesDir = context.getFilesDir();
                    File file = new File(filesDir, fileName + ".jpg");
                    FileOutputStream outStream = new FileOutputStream(file);
                    byte[] bytes = new byte[1024];
                    int tmp = -1;
                    while((tmp = stream.read(bytes)) != -1){
                        outStream.write(bytes,0,tmp);
                    }
                    stream.close();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public  static Bitmap getDrawable(final Context context, final String fileName){
        try {
            File filesDir = context.getFilesDir();
            File file = new File(filesDir, fileName + ".jpg");
            FileInputStream stream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
