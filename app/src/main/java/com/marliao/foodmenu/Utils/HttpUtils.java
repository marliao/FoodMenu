package com.marliao.foodmenu.Utils;

import android.net.Uri;
import android.widget.TimePicker;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    private static final int TIMEOUT_IN_MINUTES = 5000;

    public interface CallBack {
    }

    public static void doGetAsyn(final String urlStr, CallBack callBack){
        new Thread(){
            @Override
            public void run() {
                doGet(urlStr);
                super.run();
            }
        }.start();
    }

    private static String  doGet(String urlStr) {
        HttpURLConnection connection=null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT_IN_MINUTES);
            connection.setConnectTimeout(TIMEOUT_IN_MINUTES);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            if ()

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
