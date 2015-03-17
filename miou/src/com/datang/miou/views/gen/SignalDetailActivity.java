package com.datang.miou.views.gen;

import com.datang.miou.R;
import com.datang.miou.datastructure.Signal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SignalDetailActivity extends FragmentActivity {

	private TextView mTitleTextView;
	private TextView mExportButton;
	private ImageView mBackButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signal_detail);   
		Signal signal = (Signal) getIntent().getExtras().getSerializable(GenSignalFragment.EXTRA_SIGNAL);
		
        mTitleTextView = (TextView) findViewById(R.id.app_title_value);
		mTitleTextView.setText(R.string.gen_map_signal_detail_title);
		
		mExportButton = (TextView) findViewById(R.id.app_title_right_txt);
		mExportButton.setText(R.string.gen_map_title_export_button);
		mExportButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
			}
		});
		
		mBackButton = (ImageView) findViewById(R.id.app_title_left);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 自动生成的方法存根
				try {
					if (NavUtils.getParentActivityName((Activity) getApplicationContext()) != null) {
						NavUtils.navigateUpFromSameTask((Activity) getApplicationContext());
					}
				} catch (Exception e) {
					finish();
				}		
			}
		});
			
		TextView tv = (TextView) findViewById(R.id.detail_textView);
		tv.setText(signal.getContent());
	}

}
