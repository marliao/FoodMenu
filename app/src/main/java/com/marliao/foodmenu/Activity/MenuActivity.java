package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.IsInternet;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.db.dao.categoryTypeDao;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.Sort;
import com.marliao.foodmenu.db.doman.Types;

import org.json.JSONException;

import java.util.List;

import com.marliao.foodmenu.db.dao.menuDao;
public class MenuActivity extends AppCompatActivity {

    private static final int DATA = 100;
    private static final int MENULIST = 101;
    private GridView menu_one;
    private List<Types> mTypesList;
    private boolean mNetworkAvalible;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA:
                    MyAdapter myAdapter = new MyAdapter(mTypesList);
                    menu_one.setAdapter(myAdapter);
                    break;
                case MENULIST:
                    startActivity(new Intent(MenuActivity.this, vep_MenuActivity.class));
                    break;
            }
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

    /**
     * 将数据保存到数据库
     */
    private void initdb() {
        //将首页菜系数据存入数据库
        categoryTypeDao categoryTypeDao = new categoryTypeDao(this);
        categoryTypeDao.insertTypeList(mTypesList);
    }

    /**
     * 获取二级页面的数据
     *
     * @param typeid
     */
    private void getMenuList(final int typeid) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String menusResult = GenerateJson.generateMenus(typeid);
                    String httpResult = HttpUtils.doPost(MyApplication.pathMenuMenus, menusResult);
                    FoodMenu foodMenu = ResolveJson.resolveFoodMenu(httpResult);
                    //将数据保存到数据库
                    menuDao instanceMenu = menuDao.getInstanceMenu(MenuActivity.this);
                    instanceMenu.insertMenuList(foodMenu.getMenuList());

                    MyApplication.setFoodMenu(foodMenu);

                    Message msg = new Message();
                    msg.what = MENULIST;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    private void initData() {
        mNetworkAvalible = IsInternet.isNetworkAvalible(MenuActivity.this);
        if (mNetworkAvalible) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String httpResult = HttpUtils.doPost(MyApplication.pathMenuTypes, null);
                        Sort resolveSort = ResolveJson.resolveSort(httpResult);
                        MyApplication.setSort(resolveSort);
                        mTypesList = resolveSort.getTypesList();
                        initdb();
                        Message msg = new Message();
                        msg.what = DATA;
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            Log.i("**************","有网络状态");
            //将数据存入数据库
        }else {
            new Thread(){
                @Override
                public void run() {
                    categoryTypeDao categoryTypeDao = new categoryTypeDao(MenuActivity.this);
                    mTypesList = categoryTypeDao.findAll();
                    Message msg = new Message();
                    msg.what = DATA;
                    mHandler.sendMessage(msg);
                    super.run();
                }
            }.start();
            Log.i("**************","无网络状态");
            MyApplication.showToast("无网络连接");
        }
    }

    private void initUI() {
        menu_one = (GridView) findViewById(R.id.menu_one);
        menu_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMenuList(mTypesList.get(position).getTypeid());
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private List<Types> typesList;

        public MyAdapter(List<Types> mTypesList) {
            this.typesList = mTypesList;
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
                convertView = View.inflate(MenuActivity.this, R.layout.menu_vep, null);
                holder.img = convertView.findViewById(R.id.img);
                holder.Itext = convertView.findViewById(R.id.Itext);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img.setBackgroundDrawable(getdrawable.getdrawable(getItem(position).getTypepic(),MenuActivity.this));
            holder.Itext.setText(getItem(position).getTypename());
            return convertView;
        }
    }

    class ViewHolder {
        ImageView img;
        TextView Itext;
    }
}