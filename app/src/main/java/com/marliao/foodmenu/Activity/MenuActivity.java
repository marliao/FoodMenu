package com.marliao.foodmenu.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.marliao.foodmenu.R;
import com.marliao.foodmenu.Utils.HttpUtils;
import com.marliao.foodmenu.Utils.ResolveJson;
import com.marliao.foodmenu.db.doman.Sort;

import org.json.JSONException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        testTools();
    }

    private void testTools() {
        try {
            String path="http://192.168.1.100:8080/menu/types";
            String result = HttpUtils.doPost(path, null);
            Sort sort = ResolveJson.resolveSort(result);
            Log.i("*******sort.result*****",sort.getResult());
            Log.i("*******sort.types*****", String.valueOf(sort.getTypesList().size()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
