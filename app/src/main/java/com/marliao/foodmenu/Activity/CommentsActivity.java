package com.marliao.foodmenu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.marliao.foodmenu.Utils.DownPhotoUtil;
import com.marliao.foodmenu.Utils.GenerateJson;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.db.doman.Comment;
import com.marliao.foodmenu.db.doman.Comments;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Ptime;

import org.json.JSONException;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private static final int DATA = 100;
    private static final int CONNENCTION_OK = 101;
    private Button btn_send;
    private TextView tv_food_comments;
    private ImageView iv_food_image;
    private ListView lv_others_comments;
    private EditText et_your_comment;
    private List<Comment> mCommentList;
    private Menu mMenu;
    private MyAdapter myAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA:
                    myAdapter = new MyAdapter(mCommentList);
                    if (myAdapter != null) {
                        lv_others_comments.setAdapter(myAdapter);
                    }else {
                        myAdapter.notifyDataSetChanged();
                    }
                    break;
                case CONNENCTION_OK:
                    String responseResult = (String) msg.obj;
                    if (responseResult.equals("ok")) {
                        MyApplication.showToast("评论成功");
                        newData();
                    } else {
                        MyApplication.showToast("评论失败");
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        //初始化控件
        initUI();
        //初始化数据
        initData();
        //发送评 论
        sendComment();
    }

    private void initData() {
        Comments comments = MyApplication.getComments();
        mCommentList = comments.getCommentList();
        MenuDetail menuDetail = MyApplication.getMenuDetail();
        mMenu = menuDetail.getMenu();
        tv_food_comments.setText(mMenu.getMenuname() + "的评论");
        iv_food_image.setBackgroundDrawable(getdrawable.getdrawable(mMenu.getSpic(), CommentsActivity.this));
        //TODO 待测试  给图片设置长按点击事件，长按弹出对话框，确定则保存图片到本地
        iv_food_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CommentsActivity.this);
                builder.setTitle("图片保存");
                builder.setMessage("是否保存图片？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //保存图片操作
                        String url=MyApplication.Http+mMenu.getSpic();
                        DownPhotoUtil.donwloadImg(CommentsActivity.this,url);
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                builder.show();
                return true;
            }
        });
        Message msg = new Message();
        msg.what=DATA;
        mHandler.sendMessage(msg);

    }

    private void sendComment() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yourComment = et_your_comment.getText().toString().trim();
                et_your_comment.setText("");
                if (yourComment != null && !TextUtils.isEmpty(yourComment)) {
                    if (myAdapter != null) {
                        myAdapter.notifyDataSetChanged();
                    }
                    getConnectionForResult(yourComment);
                } else {
                    MyApplication.showToast("评论框不能为空！");
                }
            }
        });
    }

    private void getConnectionForResult(final String yourComment) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String generateComment = GenerateJson.generatePostComment(mMenu.getMenuid(), yourComment);
                    String httpResult = HttpUtils.doPost(MyApplication.pathMenuPostComment, generateComment);
                    String responseResult = ResolveJson.resolveResponseComment(httpResult);
                    Message msg = new Message();
                    msg.what = CONNENCTION_OK;
                    msg.obj = responseResult;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    /**
     * 评论成功后更新评论列表
     */
    private void newData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String commentResult = GenerateJson.generateComment(mMenu.getMenuid());
                    String jsonResult = HttpUtils.doPost(MyApplication.pathMenuComments, commentResult);
                    Comments comments = ResolveJson.resolveComments(jsonResult);
                     mCommentList=comments.getCommentList();
                    Message msg = new Message();
                    msg.what = DATA;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    private void initUI() {
        tv_food_comments = (TextView) findViewById(R.id.tv_food_comments);
        iv_food_image = (ImageView) findViewById(R.id.iv_food_image);
        lv_others_comments = (ListView) findViewById(R.id.lv_others_comments);
        et_your_comment = (EditText) findViewById(R.id.et_your_comment);
        btn_send = (Button) findViewById(R.id.btn_send);
    }

    private class MyAdapter extends BaseAdapter {

        private List<Comment> commentList;

        public MyAdapter(List<Comment> mCommentList) {
            this.commentList = mCommentList;
        }

        @Override
        public int getCount() {
            return commentList.size();
        }

        @Override
        public Comment getItem(int position) {
            return commentList.get(position);
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
            holder.tv_username.setText(getItem(position).getRegion());
            Ptime ptime = getItem(position).getPtime();
            holder.tv_date.setText(ptime.getYear() + "年" + ptime.getMonth() + "月" + ptime.getDate() + "日");
            int hours = Integer.parseInt(ptime.getHours());
            String str = null;
            if (hours > 12) {
                str = "PM";
            } else {
                str = "AM";
            }
            holder.tv_time.setText(ptime.getHours() + ":" + ptime.getMinutes() + str);
            holder.tv_comments_content.setText(getItem(position).getContent());
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
