package com.marliao.foodmenu.Activity;

import android.app.Activity;
 import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;

public class three_Activity extends Activity {

    private ImageView dish_Img;
    private TextView dish_name;
    private TextView dish_brief;
    private TextView dish_list;
    private ListView dish_step;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_menu);
        intinUI();

        //制作步骤
        initDate();


    }

    private void initDate() {
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
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
