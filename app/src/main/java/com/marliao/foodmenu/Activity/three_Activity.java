package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.SpUtil;
import com.marliao.foodmenu.db.doman.Comments;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Steps;
import com.marliao.foodmenu.Utils.getdrawable;
import org.json.JSONException;

import java.net.HttpURLConnection;
import java.util.List;

public class three_Activity extends Activity {


    private ImageView dish_Img;
    private TextView dish_name;
    private TextView dish_brief;
    private TextView dish_list;
    private ListView dish_step;
    private LinearLayout ll_comment;
    private LinearLayout ll_like;
    private ImageView iv_like;
    private LinearLayout ll_dislike;
    private ImageView iv_dislike;
    private List<Steps> stepsList;
    private Menu menu;
    private MenuDetail mMenuDetail;
    private ImageView iv_collect;
    private TextView tv_title;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_menu);
        intinUI();
        //制作步骤
        initDate();
        //下方三个点击事件
        initFootClick();
        //收藏处理
        initCollect();
        //设置标题
        initTilte();
    }

    private void initTilte() {
        //获取菜单名字
        String menuname = menu.getMenuname();
        tv_title.setText(menuname);
    }

    private void initCollect() {
        //从sp中获取value值用作回显
        boolean flag = SpUtil.getBoolean(getApplicationContext(), MyApplication.KEY_COLLECT, false);
        if(flag){
            iv_collect.setBackgroundResource(R.drawable.collect);
        }else{
            iv_collect.setBackgroundResource(R.drawable.discollect);
        }
        iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean echo = SpUtil.getBoolean(getApplicationContext(), MyApplication.KEY_COLLECT, false);
                if(!echo){
                    iv_collect.setBackgroundResource(R.drawable.collect);
                }else{
                    iv_collect.setBackgroundResource(R.drawable.discollect);
                }
                SpUtil.putBoolean(getApplicationContext(),MyApplication.KEY_COLLECT,!echo);
            }
        });
    }

    private void initFootClick() {
        //评论按钮
        ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //准备评论页面的数据
                initCommentsData();
                //跳转到评论页面
                startActivity(new Intent(three_Activity.this, CommentsActivity.class));
            }
        });
        //喜欢和不喜欢按钮的点击事件
            ll_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.like = !MyApplication.like;
                    if (MyApplication.like) {
                        iv_like.setBackgroundResource(R.drawable.like);
                        iv_dislike.setBackgroundResource(R.drawable.dislike);
                    }else {
                        iv_like.setBackgroundResource(R.drawable.dislike);
                    }
                }
            });
            ll_dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.dislike = !MyApplication.dislike;
                    if (MyApplication.dislike) {
                        iv_dislike.setBackgroundResource(R.drawable.like);
                        iv_like.setBackgroundResource(R.drawable.dislike);
                    }else {
                        iv_dislike.setBackgroundResource(R.drawable.dislike);
                    }
                }
            });
        if (MyApplication.like) {
            try {
                String jsonResult = GenerateJson.generateSupport(mMenuDetail.getMenu().getMenuid(), "yes");
                String httpResult = HttpUtils.doPost(MyApplication.pathMenuSupport, jsonResult);
                String result = ResolveJson.resolveResponseComment(httpResult);
                if (result.equals("ok")) {
                    MyApplication.showToast("评论成功！");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (MyApplication.dislike){
            try {
                String jsonResult = GenerateJson.generateSupport(mMenuDetail.getMenu().getMenuid(), "no");
                String httpResult = HttpUtils.doPost(MyApplication.pathMenuSupport, jsonResult);
                String result = ResolveJson.resolveResponseComment(httpResult);
                if (result.equals("ok")) {
                    MyApplication.showToast("评论成功！");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 第四个页面的数据
     */

    private void initCommentsData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String jsonResult = GenerateJson.generateComment(mMenuDetail.getMenu().getMenuid());
                    String httpResult = HttpUtils.doPost(MyApplication.pathMenuComments, jsonResult);
                    Comments comments = ResolveJson.resolveComments(httpResult);
                    MyApplication.setComments(comments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }
    private void initDate() {
        stepsList = MyApplication.getMenuDetail().getStepsList();
        mMenuDetail = MyApplication.getMenuDetail();
        dish_step.setAdapter(new MyAdapter());

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void intinUI() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        dish_Img = (ImageView) findViewById(R.id.dish_Img);
        menu = MyApplication.getMenuDetail().getMenu();
        dish_Img.setBackgroundDrawable(getdrawable.getdrawable(menu.getSpic(),three_Activity.this));
        dish_name = (TextView) findViewById(R.id.dish_name);
        dish_name.setText(menu.getMenuname());
        dish_brief = (TextView) findViewById(R.id.dish_brief);
        dish_brief.setText(menu.getMainmaterial());
        dish_list = (TextView) findViewById(R.id.dish_list);
        dish_list.setText(menu.getAbstracts());
        dish_step = (ListView) findViewById(R.id.dish_step);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);

        //下方三个按钮的控件
        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        ll_like = (LinearLayout) findViewById(R.id.ll_like);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        ll_dislike = (LinearLayout) findViewById(R.id.ll_dislike);
        iv_dislike = (ImageView) findViewById(R.id.iv_dislike);

        if (MyApplication.like) {
            iv_like.setBackgroundResource(R.drawable.like);
            iv_dislike.setBackgroundResource(R.drawable.dislike);
        }
        if (MyApplication.dislike){
            iv_like.setBackgroundResource(R.drawable.dislike);
            iv_dislike.setBackgroundResource(R.drawable.like);
        }

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stepsList.size();
        }

        @Override
        public Steps getItem(int position) {
            return stepsList.get(position);
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
                convertView = View.inflate(three_Activity.this, R.layout.setting_activity_view, null);
                holder.stepTittle=convertView.findViewById(R.id.text1_tittle);
                holder.stepTittle2 = convertView.findViewById(R.id.text2_tittle);
                holder.step_Img = convertView.findViewById(R.id.step_Img);
                holder.dish_time=convertView.findViewById(R.id.dish_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.stepTittle.setText("步骤："+getItem(position).getStepid());
            holder.stepTittle2.setText(getItem(position).getDescription());
            holder.step_Img.setBackgroundDrawable(getdrawable.getdrawable(getItem(position).getPic(), three_Activity.this));
            holder.dish_time.setText("10min");
            return convertView;
        }
    }

    class ViewHolder {
        TextView stepTittle;
        TextView stepTittle2;
        ImageView step_Img;
        TextView dish_time;
    }

}
