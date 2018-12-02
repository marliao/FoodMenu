package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Steps;

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
    private List<Steps> stepsList;
    private Menu menu;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_menu);
        stepsList = MyApplication.getMenuDetail().getStepsList();
        intinUI();

        //制作步骤
        initDate();

        //评论控件
        intiComment();

        //菜品简介
        intiIntro();
        //菜品名称
        intiName();
        //原料清单
        intiiList();
        //图片上传
        intiImg();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void intiImg() {
        dish_Img = (ImageView) findViewById(R.id.dish_Img);
        dish_Img.setBackground(getdrawable.getdrawable(menu.getSpic(),three_Activity.this));
    }

    private void intiiList() {
        dish_list = (TextView) findViewById(R.id.dish_list);
        dish_list.setText(menu.getMainmaterial());
    }

    private void intiName() {
        dish_name = (TextView) findViewById(R.id.dish_name);
        dish_brief.setText(menu.getMenuname());
    }

    private void intiIntro() {
        dish_brief = (TextView) findViewById(R.id.dish_brief);
        menu = MyApplication.getMenuDetail().getMenu();
        dish_brief.setText(menu.getAbstracts());
    }

    //给评论设计一个点击事件
    private void intiComment() {
        comment = (TextView) findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(three_Activity.this, CommentsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDate() {


        /*
        stepName = new String[]{"步骤一","步骤一","步骤一","步骤一","步骤一"};
        stepCourse = new String[]{"鲁菜，是起源 于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",
                "鲁菜，是起源于山东的齐鲁风味是起源于山东的齐鲁风味",};
        dishImg = new int[]{R.drawable.home_trojan,R.drawable.home_trojan,
                R.drawable.home_trojan,R.drawable.home_trojan,
                R.drawable.home_trojan};
        dishTime = new String[]{"10min","10min","10min","10min","10min","10min",};
         */
        dish_step.setAdapter(new MyAdapter());
    }

    private void intinUI() {
        dish_step = (ListView) findViewById(R.id.dish_step);
    }
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return stepsList.size();
        }

        @Override
        public Steps getItem(int position) {
            return stepsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(three_Activity.this,R.layout.setting_activity_view,null);
            TextView stepTittle = (TextView) view.findViewById(R.id.text1_tittle);
            TextView stepTittle2 = (TextView) view.findViewById(R.id.text2_tittle);
            ImageView step_Img = (ImageView) findViewById(R.id.step_Img);
            TextView time = (TextView) findViewById(R.id.time);
            //Steps stemp=stepsList.get(position);
            stepTittle.setText(getItem(position).getStepid());
            stepTittle2.setText(getItem(position).getDescription());
            step_Img.setBackground(getdrawable.getdrawable(getItem(position).getPic(),three_Activity.this));
            time.setText("10min");
            return view;
        }
    }
}
