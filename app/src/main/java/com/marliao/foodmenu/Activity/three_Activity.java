package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.marliao.foodmenu.Utils.IsInternet;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.Utils.SaveDrawableUtil;
import com.marliao.foodmenu.Utils.SpUtil;
import com.marliao.foodmenu.Utils.getdrawable;
import com.marliao.foodmenu.db.dao.EchoDao;
import com.marliao.foodmenu.db.dao.commentsDao;
import com.marliao.foodmenu.db.dao.menuDao;
import com.marliao.foodmenu.db.doman.Comment;
import com.marliao.foodmenu.db.doman.Comments;
import com.marliao.foodmenu.db.doman.Echo;
import com.marliao.foodmenu.db.doman.FoodMenu;
import com.marliao.foodmenu.db.doman.Menu;
import com.marliao.foodmenu.db.doman.MenuDetail;
import com.marliao.foodmenu.db.doman.Steps;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class three_Activity extends Activity {


    private static final int COMMENTDATA = 100;
    private static final int COLLECTDATA = 101;
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
    private commentsDao mCommentsDao;
    private EchoDao echoDao;
    private int mMenuid;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case COMMENTDATA:
                    //跳转评论页面
                    startActivity(new Intent(three_Activity.this, CommentsActivity.class));
                    break;
                case COLLECTDATA:
                    FoodMenu foodMenu = (FoodMenu) msg.obj;
                    MyApplication.setFoodMenu(foodMenu);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private boolean mNetworkAvalible;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_menu);
        //初始化echo数据
        initEcho();
        //初始化UI数据
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

    private void initEcho() {
        echoDao = EchoDao.getInstanceMenuDetail(getApplicationContext());
        //获取第二页给的menu对象中的menuid
        menu = MyApplication.getMenuDetail().getMenu();
        mMenuid = menu.getMenuid();
        new Thread(){
            public void run() {
                Echo colleck = echoDao.findById(mMenuid);
                if( colleck == null){
                    echoDao.insertMenuid(mMenuid);
                }
            }
        }.start();
    }

    private void initTilte() {
        //获取菜单名字
        String menuname = menu.getMenuname();
        tv_title.setText(menuname);
    }

    private void initCollect() {
        //从sp中获取value值用作回显
        if (echoDao.findMenuidColleck(mMenuid) == 1) {
            iv_collect.setBackgroundResource(R.drawable.collect);
        } else {
            iv_collect.setBackgroundResource(R.drawable.discollect);
        }
        iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (echoDao.findMenuidColleck(mMenuid) == 0) {
                    iv_collect.setBackgroundResource(R.drawable.collect);
                    echoDao.updateMenuidColleck(mMenuid, 1);
                } else {
                    iv_collect.setBackgroundResource(R.drawable.discollect);
                    echoDao.updateMenuidColleck(mMenuid, 0);
                }
                if (MyApplication.iscollect) {
                    //更新收藏列表数据
                    prepareCollectData();
                }
            }
        });
    }

    private void prepareCollectData() {
        new Thread() {
            @Override
            public void run() {
                EchoDao echoDao = EchoDao.getInstanceMenuDetail(three_Activity.this);
                menuDao menuD = menuDao.getInstanceMenu(three_Activity.this);
                List<Menu> menuList = new ArrayList<Menu>();
                List<Echo> echoList = echoDao.findAll();
                for (Echo echo : echoList) {
                    if (echo.getIsColleck() == 1) {
                        Menu menu = menuD.findByID(echo.getMenuid());
                        menuList.add(menu);
                    }
                }
                //获取foodmenu对象存入MyApplication 中
                FoodMenu foodmenu = new FoodMenu();
                foodmenu.setMenuList(menuList);
                foodmenu.setResult("cellect");
                Message msg = Message.obtain();
                msg.what=COLLECTDATA;
                msg.obj=foodmenu;
                mHandler.sendMessage(msg);
                super.run();
            }
        }.start();
    }

    private void initFootClick() {
        //评论按钮
        ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mNetworkAvalible = IsInternet.isNetworkAvalible(three_Activity.this);
                if(mNetworkAvalible){
                    SpUtil.putBoolean(getApplicationContext(),"INTINETFOUR"+MyApplication.numberFour, mNetworkAvalible);
                    //准备评 论页面的数据
                    initCommentsData();
                    }else if(SpUtil.getBoolean(getApplicationContext(),"INTINETFOUR"+MyApplication.numberFour,false)){
                    //准备评 论页面的数据
                    initCommentsData();
                }else {
                    MyApplication.showToast("无网络连接，请稍后重试!");
                }
            }
        });
        //喜欢和不喜欢按钮的点击事件
        ll_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //当前页面的数据
                if (echoDao.findMenuidLike(mMenuid) == 1) {
                    iv_like.setBackgroundResource(R.drawable.dislike);
                    menu.setLikes(menu.getLikes() - 1);
                    echoDao.updateMenuidLike(mMenuid, 0);
                    MyApplication.showToast("喜欢人数:" + menu.getLikes());
                } else {
                    iv_like.setBackgroundResource(R.drawable.like);
                    //判断不喜欢的状态，为true变成false
                    if (echoDao.findMenuidNotLike(mMenuid) == 1) {
                        iv_dislike.setBackgroundResource(R.drawable.dislike);
                        echoDao.updateMenuidNotLike(mMenuid, 0);
                        menu.setNotlikes(menu.getNotlikes() - 1);
                    }
                    menu.setLikes(menu.getLikes() + 1);
                    echoDao.updateMenuidLike(mMenuid, 1);
                    MyApplication.showToast("喜欢人数:" + menu.getLikes());
                }
            }
        });
        ll_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (echoDao.findMenuidNotLike(mMenuid) == 1) {
                    iv_dislike.setBackgroundResource(R.drawable.dislike);
                    menu.setNotlikes(menu.getNotlikes() - 1);
                    echoDao.updateMenuidNotLike(mMenuid, 0);
                    MyApplication.showToast("不喜欢人数:" + menu.getNotlikes());
                } else {
                    iv_dislike.setBackgroundResource(R.drawable.like);
                    if (echoDao.findMenuidLike(mMenuid) == 1) {
                        iv_like.setBackgroundResource(R.drawable.dislike);
                        echoDao.updateMenuidLike(mMenuid, 0);
                        menu.setLikes(menu.getLikes() - 1);
                    }
                    menu.setNotlikes(menu.getNotlikes() + 1);
                    echoDao.updateMenuidNotLike(mMenuid, 1);
                    MyApplication.showToast("不喜欢人数:" + menu.getNotlikes());
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
        if (MyApplication.dislike) {
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
        if (IsInternet.isNetworkAvalible(getApplicationContext())) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String jsonResult = GenerateJson.generateComment(mMenuDetail.getMenu().getMenuid());
                        String httpResult = HttpUtils.doPost(MyApplication.pathMenuComments, jsonResult);
                        Comments comments = ResolveJson.resolveComments(httpResult);
                        if (comments!=null) {
                            MyApplication.setComments(comments);
                            //获取数据将数据存入到数 据库中
                            List<Comment> commentList = comments.getCommentList();
                            int deleteAll = mCommentsDao.deleteCommentsAll();
                            System.out.println("删除了" + deleteAll + "行");
                            mCommentsDao.insertCommentList(commentList);
                        }
                        Message msg = Message.obtain();
                        msg.what=COMMENTDATA;
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();
        } else {
            //访问不到网络异常
            new Thread() {
                @Override
                public void run() {
                    List<Comment> commentsList = mCommentsDao.findAll(mMenuid);
                    Comments comments = new Comments();
                    comments.setCommentList(commentsList);
                    MyApplication.setComments(comments);
                    Message msg = Message.obtain();
                    msg.what=COMMENTDATA;
                    mHandler.sendMessage(msg);
                }
            }.start();
        }
    }

    private void initDate() {
        mCommentsDao = commentsDao.getInstanceComments(getApplicationContext());
        stepsList = MyApplication.getMenuDetail().getStepsList();
        mMenuDetail = MyApplication.getMenuDetail();
        dish_step.setAdapter(new MyAdapter());

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void intinUI() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        dish_Img = (ImageView) findViewById(R.id.dish_Img);
        dish_Img.setBackgroundDrawable(getdrawable.getdrawable(menu.getSpic(), three_Activity.this));
        // 给图片设置长按点击事件，长按弹出对话框，确定则保存图片到本地
        dish_Img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String url = MyApplication.Http + menu.getSpic();
                SaveDrawableUtil.longPressClick(url, three_Activity.this);
                return true;
            }
        });
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

        if (echoDao.findMenuidLike(mMenuid) == 1) {
            iv_like.setBackgroundResource(R.drawable.like);
            iv_dislike.setBackgroundResource(R.drawable.dislike);
        }
        if (echoDao.findMenuidNotLike(mMenuid) == 1) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(three_Activity.this, R.layout.setting_activity_view, null);
                holder.stepTittle = convertView.findViewById(R.id.text1_tittle);
                holder.stepTittle2 = convertView.findViewById(R.id.text2_tittle);
                holder.step_Img = convertView.findViewById(R.id.step_Img);
                holder.dish_time = convertView.findViewById(R.id.dish_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.stepTittle.setText("步骤：" + getItem(position).getStepid());
            holder.stepTittle2.setText(getItem(position).getDescription());
            String fileName = "stepData" + position;
            String spic = getItem(position).getPic();
            if (IsInternet.isNetworkAvalible(getApplicationContext())) {
                holder.step_Img.setBackgroundDrawable(getdrawable.getdrawable(spic, three_Activity.this));
                //将图片本地化
                SaveDrawableUtil.putDrawable(getApplicationContext(),
                        spic, fileName);
            } else {
                //从本地获取图片
                Bitmap bitmap = SaveDrawableUtil.getDrawable(getApplicationContext(), fileName);
                holder.step_Img.setImageBitmap(bitmap);
            }
//            holder.step_Img.setBackgroundDrawable(getdrawable.getdrawable(getItem(position).getPic(), three_Activity.this));
            //给图片设置长按点击事件，长按弹出对话框，确定则保存图片到本地
            holder.step_Img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String url = MyApplication.Http + getItem(position).getPic();
                    SaveDrawableUtil.longPressClick(url, three_Activity.this);
                    return true;
                }
            });
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
