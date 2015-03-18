package com.datang.miou.views.percept.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.datang.miou.ActivitySupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 网页测试
 * Created by dingzhongchang on 2015/3/16.
 */
@AutoView(R.layout.web_activity)
public class WebActivity extends ActivitySupport {

    AtomicBoolean isStop = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView mTitleTextView = (TextView) findViewById(R.id.app_title_value);
        mTitleTextView.setText("网页测试");
        TextView mRight = (TextView) findViewById(R.id.app_title_right_txt);
        mRight.setText("编辑");
        ImageView mBackButton = (ImageView) findViewById(R.id.app_title_left);
        mBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (NavUtils.getParentActivityName((Activity) getApplicationContext()) != null) {
                        NavUtils.navigateUpFromSameTask((Activity) getApplicationContext());
                    }
                } catch (Exception e) {
                    finish();
                }
            }
        });
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, EditWebActivity.class));
            }
        });
    }

    @AfterView
    private void init() {
        final ProgressBar baidu = (ProgressBar) this.f(R.id.pb_baidu);
        final ProgressBar sina = (ProgressBar) this.f(R.id.pb_sina);
        final ProgressBar mobile = (ProgressBar) this.f(R.id.pb_mobile);
        final ProgressBar tengxu = (ProgressBar) this.f(R.id.pb_tengxu);
        final ProgressBar youku = (ProgressBar) this.f(R.id.pb_youku);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (true) {
                    final int finalProgress = progress;
                    WebActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baidu.setProgress(finalProgress);
                            sina.setProgress(finalProgress);
                            mobile.setProgress(finalProgress);
                            tengxu.setProgress(finalProgress);
                            youku.setProgress(finalProgress);
                        }
                    });
                    SystemClock.sleep(1000);
                    if (progress++ == 100 || isStop.get()) break;
                }
            }
        }).start();

        final Button webCtl = (Button) this.f(R.id.bt_web_ctl);
        webCtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStop.get()) {
                    isStop.set(true);
                    webCtl.setText("开始测试");
                } else {
                    isStop.set(false);
                    webCtl.setText("停止测试");
                }
            }
        });
    }
}
