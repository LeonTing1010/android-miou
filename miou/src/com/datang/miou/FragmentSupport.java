package com.datang.miou;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.annotation.Extra;

/**
 * 
 * 
 * @author suntongwei
 */
public class FragmentSupport extends Fragment {

	// 装载视图
	protected View mView;
	// Context
	protected Context mContext;
	// Intent
	protected Intent mIntent;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// 自动装载视图
		AutoView autoView = getClass().getAnnotation(AutoView.class);
		if (autoView != null) {
			mView = inflater.inflate(autoView.value(), container, false);
			mContext = mView.getContext();
		}

		// 获取该类所有属性
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			// 自动加载界面控件引用
			for (Field field : fields) {
				AutoView av = field.getAnnotation(AutoView.class);
				if (av != null) {
					try {
						if (av.value() != Integer.MIN_VALUE) {
							field.setAccessible(true);
							field.set(this, f(av.value()));
						} else {
							field.set(this,mContext.getResources().getIdentifier(field.getName(), "id",mContext.getPackageName()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Extra extra = field.getAnnotation(Extra.class);
					if (extra != null) {
						String val = extra.value();
						if ("".equals(val)) {
							val = field.getName();
						}
						if (getIntent().hasExtra(val)) {
							field.setAccessible(true);
							try {
								field.set(this, getIntent().getExtras().get(val));
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							if (extra.must()) {
								getActivity().finish();
							}
						}
					}
				}
			}
		}

		// 执行@AfterView的方法
		Method[] methods = getClass().getDeclaredMethods();
		for (Method method : methods) {
			AfterView afterView = method.getAnnotation(AfterView.class);
			if (afterView != null) {
				method.setAccessible(true);
				try {
					method.invoke(this, new Object[] {});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return mView;
	}
	
	
	protected View f(int id) {
		return mView.findViewById(id);
	}
	
	protected void setIntent(Intent intent) {
		mIntent = intent;
	}
	protected Intent getIntent() {
		return mIntent;
	}
}
