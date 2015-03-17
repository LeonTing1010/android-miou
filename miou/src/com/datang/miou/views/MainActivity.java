package com.datang.miou.views;

import android.content.Intent;
import android.view.View;

import com.datang.miou.ActivitySupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.views.data.DataActivity;
import com.datang.miou.views.gen.GenActivity;
import com.datang.miou.views.percept.PerceptionActivity;

/**
 * 程序主界面
 *
 * @author suntongwei
 */
@AutoView(R.layout.main)
public class MainActivity extends ActivitySupport {


    @AfterView
    private void init() {

        /**
         * 通用测试
         */
        f(R.id.main_btn_gen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(mContext, GenActivity.class));
            }
        });
        /**
         * 数据管理
         */
        f(R.id.main_btn_datas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, DataActivity.class));
            }
        });

        /**
         * 通用测试
         */
        f(R.id.main_btn_cus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, PerceptionActivity.class));
            }
        });
    }
}
