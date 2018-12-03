package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.db.dao.menuDao;
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
    private boolean mIsLoad = true;
    Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(getApplicationContext(),three_Activity.class));
        }
    };
    private menuDao mMenuDao;
    private TextView tv_title;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vep_menu);
        green_name = (ListView) findViewById(R.id.greens_name);
        mMenuDao = menuDao.getInstanceMenu(getApplicationContext());
        intiDate();
        InitTitle();
    }

    //通过id给标题进行设置
    private void InitTitle() {
        tv_title = (TextView) findViewById(R.id.tv_title);
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
                if(position == 0){
                    System.out.println("第一个条目可以点击");
                }
                new Thread(){
                    @Override
                    public void run() {
                        String json = HttpUtils.doPost(MyApplication.pathMenuDetail, GenerateJson.generatemenuDetail(position));
                        try {
                            MyApplication.setMenuDetail(ResolveJson.resolveMenuDetail(json));
                            handler.sendEmptyMessage(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        //设置滚动事件
        green_name.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                    //条件一:滚动到停止状态
                    //条件二:最后一个条目可见(最后一个条目的索引值>=数据适配器中集合的总条目个数-1)
                    if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && green_name.getLastVisiblePosition()>=menuCount-1){
                        menuCount+= 15;
                        myAdapter.notifyDataSetChanged();
                        System.out.println("触发menuCount:"+menuCount);
                    }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }



    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount(){
            if(menuCount < menuList.size()){
                return menuCount;
            }else{
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(position>16){
                System.out.println("position"+position);
            }
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(),R.layout.greens_menu_name,null);
                holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
                holder.menu_name = (TextView) convertView.findViewById(R.id.menu_name);
                convertView.setTag(holder);
            }else{
                holder  = (ViewHolder) convertView.getTag();
            }
            Menu item = getItem(position);
            holder.menu_name.setText(item.getMenuname());
            String spic = item.getSpic();
            System.out.println(spic);
            holder.img1.setBackgroundDrawable(getdrawable.getdrawable(spic,vep_MenuActivity.this));
            return convertView;
        }

        class ViewHolder{
            private ImageView img1;
            private TextView menu_name;
        }
    }
}
