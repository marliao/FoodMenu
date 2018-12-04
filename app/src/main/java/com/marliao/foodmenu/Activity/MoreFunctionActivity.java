package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marliao.foodmenu.R;

public class MoreFunctionActivity extends AppCompatActivity {

    private TextView tv_user_help;
    private TextView tv_create_man;
    private TextView tv_phone;
    private TextView tv_more_back;
    private TextView tv_app_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_function);
        initUI();
    }

    private void initUI() {

        tv_user_help = (TextView) findViewById(R.id.tv_user_help);
        tv_user_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreFunctionActivity.this,UserHelpActivity.class));
            }
        });

        tv_create_man = (TextView) findViewById(R.id.tv_create_man);
        tv_create_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreFunctionActivity.this,CreateManActivity.class));
            }
        });

        tv_app_info = (TextView) findViewById(R.id.tv_app_info);
        tv_app_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreFunctionActivity.this,AppInfoActivity.class));
            }
        });

        tv_phone = (TextView) findViewById(R.id.tv_phone);
        final String phone = tv_phone.getText().toString().trim();
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击拨号
                //拨打电话的Intent
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        tv_more_back = (TextView) findViewById(R.id.tv_more_back);
        tv_more_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreFunctionActivity.this,MenuActivity.class));
                finish();
            }
        });
    }
}
