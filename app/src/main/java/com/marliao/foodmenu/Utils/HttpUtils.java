package com.marliao.foodmenu.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.RowId;

public class HttpUtils {
    private static final int TIMEOUT_IN_MINUTES = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * doGET的异步请求
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    private static String doGet(String urlStr) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream baos=null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT_IN_MINUTES);
            connection.setConnectTimeout(TIMEOUT_IN_MINUTES);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int len=-1;
                byte[] buffer = new byte[1024];
                while ((len=inputStream.read(buffer))!=-1){
                    baos.write(buffer,0,len);
                }
                baos.flush();
            }else {
                throw new RuntimeException("responseCode is not 200 ...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return null;
    }

    /**
     * doPost异步请求
     * @param urlStr 指定的网址
     * @param params    要传递的参数
     * @param callBack
     */
    public static void doPostAsyn(final String urlStr, final String params, final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String result = doPost(urlStr,params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    private static String doPost(String urlStr, String params) {
        PrintWriter printWriter=null;
        BufferedReader bufferedReader=null;
        String result="";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("charset","utf-8");
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(TIMEOUT_IN_MINUTES);
            connection.setConnectTimeout(TIMEOUT_IN_MINUTES);
            if (params != null && !params.equals("")) {
                printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.print(params);
                printWriter.flush();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result+=line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
