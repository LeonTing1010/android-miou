package com.datang.miou.views.percept.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datang.miou.R;
import com.datang.miou.views.percept.BasePageFragment;


public class TaskPageFragment extends BasePageFragment {

	@Override
	protected View initUI(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_page_task, null);
	}
}