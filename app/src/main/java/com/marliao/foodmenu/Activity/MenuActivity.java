package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.db.doman.Sort;

import org.json.JSONException;

public class MenuActivity extends AppCompatActivity {

    private GridView menu_one;
    private String[] menu_name;
    private int[] img_mennu;
    private Sort mResolveSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        intiUI();
        intiDate();
    }

    private void intiDate() {
        try {
            String httpResult = HttpUtils.doPost(MyApplication.pathMenuTypes, null);
            mResolveSort = ResolveJson.resolveSort(httpResult);
            MyApplication.setSort(mResolveSort);
            Log.i("************",mResolveSort.getResult());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void intiUI() {
        menu_one = (GridView) findViewById(R.id.menu_one);
        menu_one.setAdapter(new MyAdapter());
        menu_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case  0:
                        Intent intent = new Intent(getApplicationContext(), vep_MenuActivity.class);
                        startActivity(intent);
                        finish();
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
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView=View.inflate(MenuActivity.this,R.layout.activity_menu,null);
                holder.img=convertView.findViewById(R.id.img);
                holder.Itext=convertView.findViewById(R.id.Itext);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.img.setBackgroundResource(img_mennu[position]);
            holder.Itext.setText(menu_name[position]);
            return convertView;
        }
    }
    class ViewHolder{
        ImageView img;
        TextView Itext;
    }
}