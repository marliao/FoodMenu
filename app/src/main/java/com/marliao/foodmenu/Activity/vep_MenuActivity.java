package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;

import java.util.List;

/**
 * 二级菜单
 */
public class vep_MenuActivity extends Activity {

    private ListView green_name;
    private List<Menu> menuList;
    Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(getApplicationContext(),three_Activity.class));
        }
    };
    private List<Menu> menuListAll;
    private menuDao mMenuDao;
    private Button bt_pageUp;
    private Button bt_pageNext;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vep_menu);
        green_name = (ListView) findViewById(R.id.greens_name);
        mMenuDao = menuDao.getInstanceMenu(getApplicationContext());
        bt_pageUp = (Button) findViewById(R.id.bt_pageUp);
        bt_pageNext = (Button) findViewById(R.id.bt_pageNext);
        intiDate();

    }

    private void intiDate() {
        FoodMenu foodMenu = MyApplication.getFoodMenu();
        menuListAll = foodMenu.getMenuList();
        useMenudb();
        green_name.setAdapter(new MyAdapter());
        //给条目设置一个点击事件
        green_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
        bt_pageUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_pageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void useMenudb() {
        //将获取的数据存入到数据库中
        mMenuDao.insertMenuList(menuListAll);
        //进行分页查询
        menuList = mMenuDao.findLimit(0);

    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount()
        {
            return menuList.size();
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
