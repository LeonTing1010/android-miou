package com.datang.miou.widget;

import com.datang.miou.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * 应用程序标题
 * 
 * @author suntongwei
 */
public class AppTitle extends RelativeLayout {

	public AppTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this, true);
	}

}
