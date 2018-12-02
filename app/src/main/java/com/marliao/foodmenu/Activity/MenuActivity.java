package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.marliao.foodmenu.R;

public class MenuActivity extends AppCompatActivity {

    private GridView menu_one;
    private String[] menu_name;
    private int[] img_mennu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        testTools();
        intiUI();
        intiDate();
    }

    private void intiDate() {
        menu_name = new String[]{"分类","分类","分类","分类","分类","分类"};
        img_mennu = new int[]{R.drawable.home_trojan, R.drawable.home_trojan,
                R.drawable.home_trojan, R.drawable.home_trojan,
                R.drawable.home_trojan, R.drawable.home_trojan,};
        menu_one.setAdapter(new MyAdapter());
        menu_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case  0:
                        Intent intent = new Intent(getApplicationContext(), vep_MenuActivity.class);
                        startActivity(intent);
                        break;
                    case  1:
                        break;
                    case  2:
                        break;
                    case  3:
                        break;
                    case  4:
                        break;
                    case  5:
                        break;
                }
            }
        });
    }

    private void intiUI() {
        menu_one = (GridView) findViewById(R.id.menu_one);
    }

    private void testTools() {

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return menu_name.length;
        }

        @Override
        public Object getItem(int position) {
            return menu_name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.menu_vep, null);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView Itext = (TextView) view.findViewById(R.id.Itext);
            img.setBackgroundResource(img_mennu[position]);
            Itext.setText(menu_name[position]);
            return view;
        }
    }
}