package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marliao.foodmenu.R;

import java.util.AbstractMap;

public class CreateManActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_man);
        TextView tv_create_back=findViewById(R.id.tv_create_back);
        tv_create_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateManActivity.this,MoreFunctionActivity.class));
                finish();
            }
        });
    }
}
