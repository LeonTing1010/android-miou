/**
 * 
 */
package com.datang.miou.views.percept.task;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datang.miou.ActivitySupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;

/**
 * @author dingzhongchang
 *
 */
@AutoView(R.layout.newtask_activity)
public class NewTaskActivity extends ActivitySupport {

	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        TextView mTitleTextView = (TextView) findViewById(R.id.app_title_value);
	        mTitleTextView.setText("连接测试");
	        TextView mRight = (TextView) findViewById(R.id.app_title_right_txt);
	        mRight.setText("完成");
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

	    @AfterView
	    private void init() {

	    }
}
