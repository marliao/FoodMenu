package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.GetPicUtil;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Menu;

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

        }
    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vep_menu);
        green_name = (ListView) findViewById(R.id.greens_name);
        intiDate();
    }

    private void intiDate() {
        FoodMenu foodMenu = MyApplication.getFoodMenu();
        menuList = foodMenu.getMenuList();
        green_name.setAdapter(new MyAdapter());

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
            Drawable pic = GetPicUtil.getPic(spic);
            holder.img1.setBackgroundDrawable(pic);
            return convertView;
        }

        class ViewHolder{
            private ImageView img1;
            private TextView menu_name;
        }
    }
}
