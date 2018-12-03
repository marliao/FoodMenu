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

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
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
                    if (MyApplication.like) {
                        iv_like.setBackgroundResource(R.drawable.dislike);
                        iv_dislike.setBackgroundResource(R.drawable.like);
                    }else {
                        iv_like.setBackgroundResource(R.drawable.like);
                        iv_dislike.setBackgroundResource(R.drawable.dislike);
                    }
                    MyApplication.like = !MyApplication.like;
                }
            });
            ll_dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.dislike) {
                        iv_dislike.setBackgroundResource(R.drawable.dislike);
                        iv_like.setBackgroundResource(R.drawable.like);
                    }else {
                        iv_dislike.setBackgroundResource(R.drawable.like);
                        iv_like.setBackgroundResource(R.drawable.dislike);
                    }
                    MyApplication.dislike = !MyApplication.dislike;
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

        //下方三个按钮的控件
        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        ll_like = (LinearLayout) findViewById(R.id.ll_like);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        ll_dislike = (LinearLayout) findViewById(R.id.ll_dislike);
        iv_dislike = (ImageView) findViewById(R.id.iv_dislike);

        if (MyApplication.like) {
            iv_like.setBackgroundResource(R.drawable.like);
        }
        if (MyApplication.dislike) {
            iv_like.setBackgroundResource(R.drawable.like);
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
