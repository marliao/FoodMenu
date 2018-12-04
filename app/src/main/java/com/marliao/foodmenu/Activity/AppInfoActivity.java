package com.marliao.foodmenu.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.marliao.foodmenu.R;

import java.util.HashMap;
import java.util.Map;

public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        TextView tv_version_code = (TextView) findViewById(R.id.tv_version_code);
        TextView tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        Map<String, Object> version = getVersionName();
        tv_version_code.setText("版本号："+version.get("versionCode"));
        tv_version_name.setText("版本名称："+version.get("versionName"));
    }

    /**
     * 获取版本名称：在清单文件中
     *
     * @return 应用版本名称
     */
    private Map<String ,Object> getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(getPackageCodePath(), 0);
        String versionName = packageArchiveInfo.versionName;
        int versionCode = packageArchiveInfo.versionCode;
        Map<String,Object> version=new HashMap<>();
        version.put("versionName",versionName);
        version.put("versionCode", versionCode);
        return version;
    }

}
