package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
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

    private ImageView img_One;
    private GridView menu_One;
    private int[] img_menu;
    private String[] text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        img_One = (ImageView) findViewById(R.id.img_one);
        //初始化控件menu
        intiUI();
        //初始化数据
        intiDate();
    }

    private void intiDate() {
        text = new String[]{"分类","分类","分类","分类","分类","分类"};
        img_menu = new int[]{R.drawable.home_trojan,R.drawable.home_trojan,
                R.drawable.home_trojan,R.drawable.home_trojan,
                R.drawable.home_trojan,R.drawable.home_trojan};
        menu_One.setAdapter(new MyAdapterImpl());
        menu_One.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(getApplicationContext(),vep_MenuActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
    }

    private void intiUI() {
        menu_One = (GridView) findViewById(R.id.menu_one);
    }


    public class MyAdapterImpl extends BaseAdapter {
        @Override
        public int getCount() {
            return text.length;
        }

        @Override
        public Object getItem(int position) {
            return text[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(getApplicationContext(),R.layout.menu_vep,null);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView Itext = (TextView) view.findViewById(R.id.Itext);
            img.setBackgroundResource(img_menu[position]);
            Itext.setText(text[position]);
            return view;
        }
    }
}
