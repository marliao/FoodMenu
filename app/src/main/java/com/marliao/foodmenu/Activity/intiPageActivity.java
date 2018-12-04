package com.marliao.foodmenu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.marliao.foodmenu.R;

import com.marliao.foodmenu.Utils.IsInternet;
import com.marliao.foodmenu.Utils.SpUtil;

import static android.support.constraint.Constraints.TAG;

public class intiPageActivity extends Activity {

    private static final int ENTTER_HOME = 101;
    private static final int ENTTER_NOT = 102;
    private boolean networkAvalible;
    private RelativeLayout page;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENTTER_HOME:
                    intiMain();
                    break;
                case ENTTER_NOT:
                    intiNptNet();
                    break;
            }
        }
    };

    private void intiNptNet() {
        Intent intent=new Intent(this,notnet_Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intipage);
        page = (RelativeLayout) findViewById(R.id.page);
        //初始化动画
        intiCartoon();
        //菜单主页面

        networkAvalible = IsInternet.isNetworkAvalible(intiPageActivity.this);
        if(networkAvalible){
            SpUtil.putBoolean(getApplicationContext(),"NETPAGEONE",networkAvalible);
           handler.sendEmptyMessageDelayed(ENTTER_HOME,4000);
       }else if(SpUtil.getBoolean(getApplicationContext(),"NETPAGEONE",false)){
            handler.sendEmptyMessageDelayed(ENTTER_HOME,4000);
       }else {
            handler.sendEmptyMessageDelayed(ENTTER_NOT,4000);
        }
    }

    private void intiMain() {
        Intent intent=new Intent(this,MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void intiCartoon() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(3000);
        page.startAnimation(animation);
    }
}
