package com.marliao.foodmenu.Utils;

import android.graphics.drawable.Drawable;

import com.marliao.foodmenu.Application.MyApplication;

import java.io.InputStream;
import java.net.URL;

public class GetPicUtil {
    private static Drawable drawable;

    public static Drawable getPic(final String source) {
        new Thread() {
            @Override
            public void run() {
                String path = MyApplication.Http + source;
                try {
                    InputStream inputStream = new URL(path).openStream();
                    drawable = Drawable.createFromStream(inputStream, null);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
        return drawable;
    }
}
