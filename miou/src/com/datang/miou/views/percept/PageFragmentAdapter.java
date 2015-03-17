package com.datang.miou.views.percept;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageFragmentAdapter extends FragmentPagerAdapter {

	private BasePageFragment[] mPages = null;

	public PageFragmentAdapter(FragmentManager fm, BasePageFragment[] pages) {
		super(fm);
		mPages = pages;
	}

	@Override
	public int getCount() {
		if (mPages != null) {
			return mPages.length;
		} else {
			return 0;
		}
	}

	@Override
	public Fragment getItem(int index) {
		if (mPages != null && index > -1 && index < mPages.length) {
			return mPages[index];
		} else {
			return null;
		}
	}
}