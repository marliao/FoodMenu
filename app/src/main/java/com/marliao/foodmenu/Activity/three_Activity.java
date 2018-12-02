package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.marliao.foodmenu.db.doman.MenuDetail;

import org.json.JSONException;

import java.net.HttpURLConnection;

public class three_Activity extends Activity {

    private ImageView dish_Img;
    private TextView dish_name;
    private TextView dish_brief;
    private TextView dish_list;
    private ListView dish_step;
    private int[] dishImg;
    private String[] stepCourse;
    private String[] stepName;
    private String[] dishTime;
    private MenuDetail mMenuDetail;
    private LinearLayout ll_comment;
    private LinearLayout ll_like;
    private ImageView iv_like;
    private LinearLayout ll_dislike;
    private ImageView iv_dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_menu);
        System.out.println(MyApplication.getMenuDetail().getResult());
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
        mMenuDetail = MyApplication.getMenuDetail();


        dishImg = new int[]{R.drawable.home_trojan, R.drawable.home_trojan,
                R.drawable.home_trojan, R.drawable.home_trojan,
                R.drawable.home_trojan};
        dishTime = new String[]{"10min", "10min", "10min", "10min", "10min", "10min",};
//        dish_step.setAdapter(new MyAdapter());

    }

    private void intinUI() {
        dish_Img = (ImageView) findViewById(R.id.dish_Img);
        dish_name = (TextView) findViewById(R.id.dish_name);
        dish_brief = (TextView) findViewById(R.id.dish_brief);
        dish_list = (TextView) findViewById(R.id.dish_list);
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
            return stepName.length;
        }

        @Override
        public Object getItem(int position) {
            return stepName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(three_Activity.this, R.layout.setting_activity_view, null);
            TextView stepTittle = (TextView) view.findViewById(R.id.text1_tittle);
            TextView stepTittle2 = (TextView) view.findViewById(R.id.text2_tittle);
            ImageView step_Img = (ImageView) view.findViewById(R.id.step_Img);
            TextView time = (TextView) view.findViewById(R.id.time);
            stepTittle.setText(stepName[position]);
            stepTittle2.setText(stepCourse[position]);
            step_Img.setBackgroundResource(dishImg[position]);
            time.setText(dishTime[position]);
            return view;
        }
    }
}
