package com.marliao.foodmenu.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.marliao.foodmenu.Application.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

//获取网络图片对象
public class getdrawable {
    static Drawable drawable;
    private static URL url;
    private static String imgurl;

    public static Drawable getdrawable(final String path, final Context context) {
        imgurl = MyApplication.Http + path;
        url = null;
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    url = new URL(imgurl);
                    InputStream stream = url.openStream();
                    drawable = Drawable.createFromResourceStream(context.getResources(), null, stream, "src", null);
                    Drawable.createFromStream(url.openStream(), "");
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return drawable;
    }
}
