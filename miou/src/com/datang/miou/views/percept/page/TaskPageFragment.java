package com.datang.miou.views.percept.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datang.miou.R;
import com.datang.miou.views.percept.BasePageFragment;
import com.datang.miou.views.percept.task.NewTaskActivity;

public class TaskPageFragment extends BasePageFragment implements
		View.OnClickListener {

	@Override
	protected View initUI(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_page_task, container,
				false);
		root.findViewById(R.id.tv_add_task).setOnClickListener(this);
		return root;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_add_task:
			mContext.startActivity(new Intent(mContext, NewTaskActivity.class));
			break;

		}

	}
}