package com.marliao.foodmenu.Activity;

import android.media.Image;
import android.sax.TextElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
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
import com.marliao.foodmenu.db.doman.Comments;

public class CommentsActivity extends AppCompatActivity {

    private Button btn_send;
    private TextView tv_food_comments;
    private ImageView iv_food_image;
    private ListView lv_others_comments;
    private EditText et_your_comment;

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
    }

    private void initData() {
        Comments comments = MyApplication.getComments();
        comments.getCommentList();
    }

    private void sendComment() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yourComment = et_your_comment.getText().toString().trim();
                if (yourComment != null && !TextUtils.isEmpty(yourComment)) {
                    //TODO 拼接Json字符串，将评论内容发送出去，并将服务器返回的数据解析，告诉用户是否评论成功
                } else {
                    MyApplication.showToast("评论框不能为空！");
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
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
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
                holder.iv_comments_view = findViewById(R.id.iv_comments_image);
                holder.tv_username = findViewById(R.id.tv_username);
                holder.tv_date = findViewById(R.id.tv_date);
                holder.tv_time = findViewById(R.id.tv_time);
                holder.tv_comments_content = findViewById(R.id.tv_comments_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.iv_comments_view.setBackgroundResource(R.mipmap.ic_launcher);
                holder.tv_username.setText("未知的用户");
            }
            return null;
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
