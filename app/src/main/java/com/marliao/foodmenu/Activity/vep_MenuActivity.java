package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.R;

/**
 * 二级菜单
 */
public class vep_MenuActivity extends Activity {

    private ListView green_name;
    private String[] text_str;
    private int[] img_1;
    private int[] img_2;
    private int[] img_3;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vep_menu);
        green_name = (ListView) findViewById(R.id.greens_name);

        intiDate();

    }

    private void intiDate() {
        text_str = new String[]{"菜品1", "菜品1", "菜品1", "菜品1", "菜品1", "菜品1", "菜品1"};
        img_1 = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher};
        img_2 = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher};
        img_3 = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher};
        green_name.setAdapter(new MyAdapter());
        green_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case  0:
                        Intent intent=new Intent(getApplicationContext(),three_Activity.class);
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
                    case  6:
                        break;

                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return text_str.length;
        }

        @Override
        public Object getItem(int position) {
            return text_str[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(getApplicationContext(),R.layout.greens_menu_name,null);
            ImageView img1 = (ImageView) view.findViewById(R.id.img1);
            TextView menu_name = (TextView) view.findViewById(R.id.menu_name);
            ImageView img2 = (ImageView) view.findViewById(R.id.img2);
            ImageView img3 = (ImageView) view.findViewById(R.id.img3);
            ImageView img4 = (ImageView) view.findViewById(R.id.img4);
            ImageView img5 = (ImageView) view.findViewById(R.id.img5);
            ImageView img6 = (ImageView) view.findViewById(R.id.img6);
            img1.setBackgroundResource(img_1[position]);
            menu_name.setText(text_str[position]);
            img2.setBackgroundResource(img_2[position]);
            img3.setBackgroundResource(img_2[position]);
            img4.setBackgroundResource(img_2[position]);
            img5.setBackgroundResource(img_3[position]);
            img6.setBackgroundResource(img_3[position]);
            return view;
        }
    }
}
