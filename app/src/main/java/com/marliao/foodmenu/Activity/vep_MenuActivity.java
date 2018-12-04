package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.IsInternet;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.SaveDrawableUtil;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.db.dao.menuDao;
import com.marliao.foodmenu.db.dao.stepDao;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Steps;
import com.marliao.foodmenu.db.doman.Types;

import org.json.JSONException;

import java.util.List;

/**
 * 二级菜单
 */
public class vep_MenuActivity extends Activity {

    private static final String TAG = "vep_MenuActivity";
    private ListView green_name;
    private List<Menu> menuList;
    private int menuCount = 10;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(getApplicationContext(), three_Activity.class));
        }
    };
    private menuDao mMenuDao;
    private TextView tv_title;
    private stepDao mStepDao;
    private List<Steps> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vep_menu);
        green_name = (ListView) findViewById(R.id.greens_name);
        mMenuDao = menuDao.getInstanceMenu(getApplicationContext());
        mStepDao = stepDao.getInstanceStep(getApplicationContext());
        intiDate();
        InitTitle();

    }

    // 通过id给标题进行设置
    private void InitTitle() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        String result = MyApplication.getFoodMenu().getResult();
        if (result.equals("cellect")) {
            tv_title.setText("收藏");
            return;
        }
        int typeid = menuList.get(0).getTypeid();
        List<Types> typesList = MyApplication.getSort().getTypesList();
        String typename = typesList.get(typeid - 1).getTypename();
        tv_title.setText(typename);
    }

    private void intiDate() {
        FoodMenu foodMenu = MyApplication.getFoodMenu();
        menuList = foodMenu.getMenuList();
        final MyAdapter myAdapter = new MyAdapter();
        green_name.setAdapter(myAdapter);
        //给条目设置一个点击事件
        green_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (IsInternet.isNetworkAvalible(getApplicationContext())) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {

                                int menuid = menuList.get(position).getMenuid();
                                String json = HttpUtils.doPost(MyApplication.pathMenuDetail, GenerateJson.generatemenuDetail(menuid));
                                MenuDetail menuDetail = ResolveJson.resolveMenuDetail(json);
                                //将数据存入到数据库中
                                mStepDao.insertStepList(menuDetail.getStepsList());
                                MyApplication.setMenuDetail(menuDetail);
                                System.out.println("第一个条目可以在子线程中点击");
                                handler.sendEmptyMessage(0);
                            } catch (JSONException e) {
                                System.out.println("发生了异常");
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            //获取当前点击的menu数据
                            Menu menu = menuList.get(position);
                            stepList = mStepDao.findAll(menu.getMenuid());
                            //创建一个MenuDetail对象存入数据
                            MenuDetail detail = new MenuDetail();
                            detail.setMenu(menu);
                            detail.setStepsList(stepList);
                            MyApplication.setMenuDetail(detail);
                            handler.sendEmptyMessage(0);
                            System.out.println("menu对象数据为:-----------------------" + menu);
                        }
                    }.start();
                }
            }
        });

        //设置滚动事件
        green_name.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //条件一:滚动到停止状态
                //条件二:最后一个条目可见(最后一个条目的索引值>=数据适配器中集合的总条目个数-1)
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && green_name.getLastVisiblePosition() >= menuCount - 1) {
                    menuCount += 15;
                    myAdapter.notifyDataSetChanged();
                    System.out.println("触发menuCount:" + menuCount);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (menuCount < menuList.size()) {
                return menuCount;
            } else {
                return menuList.size();
            }
        }

        @Override
        public Menu getItem(int position) {
            return menuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (position > 16) {
                System.out.println("position" + position);
            }
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.greens_menu_name, null);
                holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
                holder.menu_name = (TextView) convertView.findViewById(R.id.menu_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Menu item = getItem(position);
            holder.menu_name.setText(item.getMenuname());
            String spic = item.getSpic();
            System.out.println(spic);
            holder.img1.setBackgroundDrawable(getdrawable.getdrawable(spic, vep_MenuActivity.this));
            holder.img1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String spic1 = getItem(position).getSpic();
                    String imgurl = MyApplication.Http + spic1;
                    SaveDrawableUtil.longPressClick(imgurl, vep_MenuActivity.this);
                    return true;
                }
            });
            return convertView;
        }

        class ViewHolder {
            private ImageView img1;
            private TextView menu_name;
        }
    }
}
