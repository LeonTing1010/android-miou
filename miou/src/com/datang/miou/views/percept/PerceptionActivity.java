package com.datang.miou.views.percept;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.datang.miou.R;


/**
 * Created by dingzhongchang on 2015/3/7.
 */
public class PerceptionActivity extends FragmentActivity implements CompoundButton.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private MainTabView mView = null;

    private int mCurrentPageIndex = MainTabView.TAB_INDEX_HOME;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new MainTabView(this);
        if (savedInstanceState == null) {
            mCurrentPageIndex = MainTabView.TAB_INDEX_HOME;
        } else {
            //TODO
        }
        mView.changeCheckedTabOnPageSelectedChanged(mCurrentPageIndex);
        mTitleTextView = (TextView) findViewById(R.id.app_title_value);
        mTitleTextView.setText("用户感知");
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
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mTitleTextView.setText(buttonView.getText());
            mView.changeSelectedPageOnTabCheckedChanged(buttonView);
        }
    }

    @Override
    public void onPageSelected(int index) {
        mView.changeCheckedTabOnPageSelectedChanged(index);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    ;


}
