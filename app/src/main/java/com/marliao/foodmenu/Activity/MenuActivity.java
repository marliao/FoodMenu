package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.GetPicUtil;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Sort;
import com.marliao.foodmenu.db.doman.Types;

import org.json.JSONException;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private GridView menu_one;
    private List<Types> mTypesList;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initAdapter();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initUI();
        initData();
    }

    private void initAdapter() {
        MyAdapter myAdapter = new MyAdapter(mTypesList);
        menu_one.setAdapter(myAdapter);
        menu_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case  0:
                        getMenuList(mTypesList.get(position).getTypeid());
                        startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                        break;
                    case  1:
                        getMenuList(mTypesList.get(position).getTypeid());
                        startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                        break;
                    case  2:
                        getMenuList(mTypesList.get(position).getTypeid());
                        startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                        break;
                    case  3:
                        getMenuList(mTypesList.get(position).getTypeid());
                        startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                        break;
                    case  4:
                        getMenuList(mTypesList.get(position).getTypeid());
                        startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                        break;
                    case  5:
                        getMenuList(mTypesList.get(position).getTypeid());
                        startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 获取二级页面的数据
     * @param typeid
     */
    private void getMenuList(int typeid) {
        try {
            String menusResult = GenerateJson.generateMenus(typeid);
            FoodMenu foodMenu = ResolveJson.resolveFoodMenu(menusResult);
            MyApplication.setFoodMenu(foodMenu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    String httpResult = HttpUtils.doPost(MyApplication.pathMenuTypes, null);
                    Sort resolveSort = ResolveJson.resolveSort(httpResult);
                    MyApplication.setSort(resolveSort);
                    mTypesList = resolveSort.getTypesList();
                    mHandler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initUI() {
        menu_one = (GridView) findViewById(R.id.menu_one);
    }

    private class MyAdapter extends BaseAdapter {

        private List<Types> typesList;

        public MyAdapter(List<Types> mTypesList) {
            this.typesList=mTypesList;
        }

        @Override
        public int getCount() {
            return typesList.size();
        }

        @Override
        public Types getItem(int position) {
            return typesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView=View.inflate(MenuActivity.this,R.layout.menu_vep,null);
                holder.img=convertView.findViewById(R.id.img);
                holder.Itext=convertView.findViewById(R.id.Itext);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            //TODO 图片显示问题
            holder.img.setBackground(GetPicUtil.getPic(getItem(position).getTypepic()));
            holder.Itext.setText(getItem(position).getTypename());
            return convertView;
        }
    }
    class ViewHolder{
        ImageView img;
        TextView Itext;
    }
}