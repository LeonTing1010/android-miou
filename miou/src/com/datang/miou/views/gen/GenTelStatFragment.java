package com.datang.miou.views.gen;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.datastructure.Chart;
import com.datang.miou.datastructure.Chart.Point;
import com.datang.miou.views.gen.telstat.GenTelStatDataConnectionFragment;
import com.datang.miou.views.gen.telstat.GenTelStatDataQualityFragment;
import com.datang.miou.views.gen.telstat.GenTelStatMessageFragment;
import com.datang.miou.views.gen.telstat.GenTelStatVoiceFragment;

/**
 * 话务统计
 * 
 * @author suntongwei
 */
@AutoView(R.layout.gen_tel_stat)
public class GenTelStatFragment extends FragmentSupport implements Chart.mCallback{

	protected static final int PARAM_PAGES = 4;
	private View mView;
	private ViewPager mViewPager;
	private TextView[] textViews;
	private TextView indicator;
	private Chart mChart;
	public Button addButton;
	
	private class ViewPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO 自动生成的方法存根
			textViews[position].setTextColor(Color.BLUE);
			for (int i = 0; i < PARAM_PAGES; i++) {		
				if (i != position) {
					textViews[i].setTextColor(Color.BLACK);
				}
			}
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		/*
		 * 这段代码为了解决切换到别的页面再返回这个PageViewer时显示不正常的问题
		 */
		if (mView != null) {
			ViewGroup parent = (ViewGroup) mView.getParent();
			if (parent != null) {
				parent.removeView(mView);
			}
			return mView;
		}
		
		mView = inflater.inflate(R.layout.gen_tel_stat, container, false);
		mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
		FragmentManager fm = getFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return PARAM_PAGES;
			}

			@Override
			public Fragment getItem(int pos) {
				// TODO 自动生成的方法存根			
				switch (pos) {
					case 0:
						return new GenTelStatVoiceFragment();
					case 1:
						return new GenTelStatMessageFragment();
					case 2:
						return new GenTelStatDataConnectionFragment();
					case 3:
						return new GenTelStatDataQualityFragment();
					default:
						return null;	
				}
				
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPageChangeListener());		
		AddPageViewrContents();

		mChart = (Chart) mView.findViewById(R.id.chart);
		mChart.setHorizontalAxeNum(5);
		mChart.setVerticalAxeNum(20);
		mChart.setCb((Chart.mCallback) this);
		
		addButton = (Button) mView.findViewById(R.id.add);
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				int data = (int) (Math.random() * 1000);
				mChart.addPoint(data);
			}
		});
		return mView;
	}

	private void AddPageViewrContents() {
		// TODO 自动生成的方法存根
		textViews = new TextView[PARAM_PAGES];
		
		indicator = (TextView) mView.findViewById(R.id.indicator_1);
		indicator.setTextColor(Color.BLUE);
		textViews[0] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_2);
		textViews[1] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_3);
		textViews[2] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_4);
		textViews[3] = indicator;
	}
	
	@Override
	public void onClickOnPoint(Point p) {
		// TODO 自动生成的方法存根
		//Toast.makeText(getApplication(), String.valueOf(p.data), Toast.LENGTH_SHORT);
		addButton.setText(String.valueOf(p.data));
	}  
}
