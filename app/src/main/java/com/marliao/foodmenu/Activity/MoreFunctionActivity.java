package com.marliao.foodmenu.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.marliao.foodmenu.R;

public class MoreFunctionActivity extends AppCompatActivity {

    private TextView tv_user_help;
    private TextView tv_create_user;
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_function);
        initUI();
    }

    private void initUI() {
        tv_user_help = (TextView) findViewById(R.id.tv_user_help);
        tv_create_user = (TextView) findViewById(R.id.tv_create_user);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
    }
}
