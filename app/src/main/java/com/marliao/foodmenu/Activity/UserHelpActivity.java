package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marliao.foodmenu.R;

public class UserHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
        TextView tv_help_back=findViewById(R.id.tv_help_back);
        tv_help_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHelpActivity.this,MoreFunctionActivity.class));
                finish();
            }
        });
    }
}
