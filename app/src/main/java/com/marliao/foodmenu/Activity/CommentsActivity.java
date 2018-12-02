package com.marliao.foodmenu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.foodmenu.Application.MyApplication;
import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.db.doman.Comment;
import com.marliao.foodmenu.db.doman.Comments;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.Ptime;

import org.json.JSONException;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private Button btn_send;
    private TextView tv_food_comments;
    private ImageView iv_food_image;
    private ListView lv_others_comments;
    private EditText et_your_comment;
    private List<Comment> mCommentList;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        //初始化控件
        initUI();
        //初始化数据
        initData();
        //发送评论
        sendComment();
        //展示数据
        initAdapter();
    }

    private void initAdapter() {
        lv_others_comments.setAdapter(new MyAdapter());
    }

    private void initData() {
        Comments comments = MyApplication.getComments();
        mCommentList = comments.getCommentList();
        MenuDetail menuDetail = MyApplication.getMenuDetail();
        mMenu = menuDetail.getMenu();
    }

    private void sendComment() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String yourComment = et_your_comment.getText().toString().trim();
                    if (yourComment != null && !TextUtils.isEmpty(yourComment)) {
                        String generateComment = GenerateJson.generatePostComment(mMenu.getMenuid(),yourComment);
                        String httpResult = HttpUtils.doPost(MyApplication.pathMenuPostComment, generateComment);
                        String responseResult = ResolveJson.resolveResponseComment(httpResult);
                        if (responseResult.equals("ok")) {
                            MyApplication.showToast("评论成功");
                        }else {
                            MyApplication.showToast("评论失败");
                        }
                    } else {
                        MyApplication.showToast("评论框不能为空！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initUI() {
        tv_food_comments = (TextView) findViewById(R.id.tv_food_comments);
        iv_food_image = (ImageView) findViewById(R.id.iv_food_image);
        lv_others_comments = (ListView) findViewById(R.id.lv_others_comments);
        et_your_comment = (EditText) findViewById(R.id.et_your_comment);
        btn_send = (Button) findViewById(R.id.btn_send);
        //给控件设置值
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCommentList.size();
        }

        @Override
        public Comment getItem(int position) {
            return mCommentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(CommentsActivity.this, R.layout.list_comments_item, null);
                holder.iv_comments_view = convertView.findViewById(R.id.iv_comments_image);
                holder.tv_username = convertView.findViewById(R.id.tv_username);
                holder.tv_date = convertView.findViewById(R.id.tv_date);
                holder.tv_time = convertView.findViewById(R.id.tv_time);
                holder.tv_comments_content = convertView.findViewById(R.id.tv_comments_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.iv_comments_view.setBackgroundResource(R.mipmap.ic_launcher);
            holder.tv_username.setText("未知的用户");
            Ptime ptime = getItem(position).getPtime();
            holder.tv_date.setText(ptime.getYear() + "年" + ptime.getMonth() + "月" + ptime.getDate() + "日");
            int hours = Integer.parseInt(ptime.getHours());
            String str = null;
            if (hours > 12) {
                str = "AM";
            } else {
                str = "PM";
            }
            holder.tv_time.setText(ptime.getHours() + ":" + ptime.getMinutes() + str);
            holder.tv_comments_content.setText("目前没有数据");
            return convertView;
        }
    }

    class ViewHolder {
        ImageView iv_comments_view;
        TextView tv_username;
        TextView tv_date;
        TextView tv_time;
        TextView tv_comments_content;
    }
}