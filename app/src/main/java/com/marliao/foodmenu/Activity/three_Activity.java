package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.db.doman.MenuDetail;

import java.util.List;

import static android.content.ContentValues.TAG;

public class three_Activity extends Activity {

    private ImageView dish_Img;
    private TextView dish_name;
    private TextView dish_brief;
    private TextView dish_list;
    private ListView dish_step;
    private int[] dishImg;
    private String[] stepCourse;
    private String[] stepName;
    private String[] dishTime;
    private TextView comment;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_menu);
        intinUI();

        //制作步骤
        initDate();

        //评论控件
        intiComment();
    }

    //给评论设计一个点击事件
    private void intiComment() {
        comment = (TextView) findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(three_Activity.this, CommentsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initDate() {


        stepName = new String[]{"步骤一","步骤一","步骤一","步骤一","步骤一"};
        stepCourse = new String[]{"鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",};
        dishImg = new int[]{R.drawable.home_trojan,R.drawable.home_trojan,
                R.drawable.home_trojan,R.drawable.home_trojan,
                R.drawable.home_trojan};
        dishTime = new String[]{"10min","10min","10min","10min","10min","10min",};
        dish_step.setAdapter(new MyAdapter());

    }

    private void intinUI() {
        dish_Img = (ImageView) findViewById(R.id.dish_Img);
        dish_name = (TextView) findViewById(R.id.dish_name);
        dish_brief = (TextView) findViewById(R.id.dish_brief);
        dish_list = (TextView) findViewById(R.id.dish_list);
        dish_step = (ListView) findViewById(R.id.dish_step);
    }
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return stepName.length;
        }

        @Override
        public Object getItem(int position) {
            return stepName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(three_Activity.this,R.layout.setting_activity_view,null);
            TextView stepTittle = (TextView) view.findViewById(R.id.text1_tittle);
            TextView stepTittle2 = (TextView) view.findViewById(R.id.text2_tittle);
            ImageView step_Img = (ImageView) findViewById(R.id.step_Img);
            TextView time = (TextView) findViewById(R.id.time);
            stepTittle.setText(stepName[position]);
            stepTittle2.setText(stepCourse[position]);
            step_Img.setBackgroundResource(dishImg[position]);
            time.setText(dishTime[position]);
            return view;
        }
    }
}
