package com.marliao.foodmenu.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.IsInternet;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.SaveDrawableUtil;
import com.marliao.foodmenu.Utils.SpUtil;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.db.dao.EchoDao;
import com.marliao.foodmenu.db.dao.categoryTypeDao;
import com.marliao.foodmenu.db.dao.menuDao;
import com.marliao.foodmenu.db.doman.Echo;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.Sort;
import com.marliao.foodmenu.db.doman.Types;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
    private Button bt_cellect;
    private Button bt_function;
    private ImageView img_one;
    private menuDao mMenuDao;
    private int mTypeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initUI();
        initData();
        //扩展点击功能
        initCellect();

    }

    private void initCellect() {
        bt_cellect = (Button) findViewById(R.id.bt_cellect);
        bt_function = (Button) findViewById(R.id.bt_function);
        //点击跳转搜藏页面
        bt_cellect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData();
            }
        });

        //点击实现更多功能
        bt_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MoreFunctionActivity.class));
            }
        });
    }

    //准备收藏数据
    private void prepareData() {
        new Thread() {
            @Override
            public void run() {
                EchoDao echoDao = EchoDao.getInstanceMenuDetail(getApplicationContext());
                menuDao menuD = menuDao.getInstanceMenu(getApplicationContext());
                List<Menu> menuList = new ArrayList<Menu>();
                List<Echo> echoList = echoDao.findAll();
                for (Echo echo : echoList) {
                    if (echo.getIsColleck() == 1) {
                        Menu menu = menuD.findByID(echo.getMenuid());
                        menuList.add(menu);
                    }
                }
                System.out.println(menuList.toString());
                //获取foodmenu对象存入MyApplication 中
                FoodMenu foodmenu = new FoodMenu();
                foodmenu.setMenuList(menuList);
                foodmenu.setResult("cellect");
                MyApplication.setFoodMenu(foodmenu);
                //发送消息进行页面跳转
                Message msg = Message.obtain();
                msg.what = MENULIST;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * 将数据保存到数据库
     */
    private void initdb() {
        //将首页菜系数据存入数据库
        categoryTypeDao categoryTypeDao = new categoryTypeDao(this);
        categoryTypeDao.deleteTypeAll();
        categoryTypeDao.insertTypeList(mTypesList);
    }

    /**
     * 获取二级页面的数据
     *
     * @param typeid
     */
    private void getMenuList(final int typeid) {
        if (mNetworkAvalible) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String menusResult = GenerateJson.generateMenus(typeid);
                        String httpResult = HttpUtils.doPost(MyApplication.pathMenuMenus, menusResult);
                        FoodMenu foodMenu = ResolveJson.resolveFoodMenu(httpResult);
                        //将菜谱列表页数据 保存到数据库
                        menuDao instanceMenu = menuDao.getInstanceMenu(MenuActivity.this);
                        instanceMenu.deleteMenu(typeid);
                        instanceMenu.insertMenuList(foodMenu.getMenuList());
                        MyApplication.setFoodMenu(foodMenu);
                        Message msg = new Message();
                        msg.what = MENULIST;
                        System.out.println("第二面数据有网络状态执行了");
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();
        } else {
            new Thread(){
                @Override
                public void run() {
                    mMenuDao = menuDao.getInstanceMenu(getApplicationContext());
                    List<Menu> menuList = mMenuDao.findAll(mTypeid);
                    System.out.println(menuList);
                    FoodMenu foodMenu = new FoodMenu();
                    foodMenu.setMenuList(menuList);
                    foodMenu.setResult("menu");
                    System.out.println("第二面数据无网络状态执行了");
                    MyApplication.setFoodMenu(foodMenu);
                    Message msg = new Message();
                    msg.what = MENULIST;
                    mHandler.sendMessage(msg);
                }
            }.start();
        }
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
            Log.i("**************", "有网络状态");
            //将数据存入数据库
        } else {
            new Thread() {
                @Override
                public void run() {
                    categoryTypeDao categoryType = categoryTypeDao.getInstanceCategoryType(MenuActivity.this);
                    mTypesList = categoryType.findAll();
                    Message msg = new Message();
                    msg.what = DATA;
                    mHandler.sendMessage(msg);
                    super.run();
                }
            }.start();
            Log.i("**************", "无网络状态");
            MyApplication.showToast("无网络连接，请稍后重试!");
        }
    }

    private void initUI() {
        menu_one = (GridView) findViewById(R.id.menu_one);
        menu_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTypeid =mTypesList.get(position).getTypeid();
                // 判断是否有网络连接
                if (mNetworkAvalible) {
                    SpUtil.putBoolean(getApplicationContext(), "NETPAGETWO" + position, mNetworkAvalible);
                    getMenuList(mTypesList.get(position).getTypeid());
                } else if (SpUtil.getBoolean(getApplicationContext(), "NETPAGETWO" + position, false)) {
                   getMenuList(mTypesList.get(position).getTypeid());
                } else {
                    MyApplication.showToast("无网络连接，请稍后重试!");
                }
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(MenuActivity.this, R.layout.menu_vep, null);
                holder.img = convertView.findViewById(R.id.img);
                holder.Itext = convertView.findViewById(R.id.Itext);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String fileName = getItem(position).getTypename() + position;
            if (mNetworkAvalible) {
                holder.img.setBackgroundDrawable(getdrawable.getdrawable(getItem(position).getTypepic(), MenuActivity.this));
                //将图片本地化
                SaveDrawableUtil.putDrawable(getApplicationContext(),
                        getItem(position).getTypepic(), fileName);
            } else {
                Bitmap bitmap;
                //判断是否为第二张图片
                if(position == 1){
                    bitmap = SaveDrawableUtil.getDrawable(getApplicationContext(),getItem(position-1).getTypename() + (position-1));
                    System.out.println("fileName:"+getItem(position-1).getTypename() + (position-1));
                }else{
                    //从本地获取图片
                    bitmap = SaveDrawableUtil.getDrawable(getApplicationContext(), fileName);
                    System.out.println("fileName:"+fileName);
                }
                holder.img.setImageBitmap(bitmap);
            }
            holder.Itext.setText(getItem(position).getTypename());

            //设置长按点击事件
            holder.img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String path = getItem(position).getTypepic();
                    String imgurl = MyApplication.Http + path;
                    SaveDrawableUtil.longPressClick(imgurl, MenuActivity.this);
                    return false;
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView img;
        TextView Itext;
    }
}