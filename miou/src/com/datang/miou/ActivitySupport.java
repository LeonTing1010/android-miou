package com.datang.miou;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.datang.miou.annotation.AfterView;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.annotation.Extra;
import com.datang.miou.annotation.Result;

/**
 * Activity支持类
 * 
 * @author suntongwei
 */
public abstract class ActivitySupport extends Activity {

	// Context
	protected Context mContext = ActivitySupport.this;

	//
	private List<Activity> activityList = new LinkedList<Activity>();

	/**
	 * onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 添加该视图进入集合
		activityList.add(this);
		
		// 自动装载视图
		AutoView autoView = getClass().getAnnotation(AutoView.class);
		if (autoView != null) {
			setContentView(autoView.value());
		}
		
		// 获取该类所有属性
		Field[] fields = getClass().getDeclaredFields();
		if(fields != null && fields.length > 0) {
			// 自动加载界面控件引用
			for(Field field : fields) {
				AutoView av = field.getAnnotation(AutoView.class);
				if(av != null) {
					try {
						if(av.value() != Integer.MIN_VALUE) {
							field.setAccessible(true);
							field.set(this, f(av.value()));
						} else {
							field.set(this, mContext.getResources().getIdentifier(field.getName(), "id", mContext.getPackageName()));
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					Extra extra = field.getAnnotation(Extra.class);
					if(extra != null) {
						String val = extra.value();
						if("".equals(val)) {
							val = field.getName();
						}
						if(getIntent().hasExtra(val)) {
							field.setAccessible(true);
							try {
								field.set(this, getIntent().getExtras().get(val));
							} catch (Exception e) {
								e.printStackTrace();
							} 
						} else {
							if(extra.must()) {
								finish();
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

	}

	/**
	 * 当Activity返回结果方法
	 * 反射对应方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Method[] methods = getClass().getDeclaredMethods();
		for (Method method : methods) {
			Result result = method.getAnnotation(Result.class);
			if (result != null && (result.value() == requestCode || result.value() == Integer.MIN_VALUE)) {
				method.setAccessible(true);
				try {
					method.invoke(this, new Object[] { requestCode, resultCode, data });
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * 按返回键监控
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_OK, new Intent());
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * onDestroy
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		activityList.remove(this);
	}

	/**
	 * 返回Context句柄
	 * 
	 * @return
	 */
	protected Context getContext() {
		return mContext;
	}

	/**
	 * 简化findViewById方法长度
	 * 
	 * @param id
	 * @return
	 */
	protected View f(int id) {
		return findViewById(id);
	}

	/**
	 * 退出应用程序
	 */
	protected void exitApp() {
		destroyActivityAll();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 销毁所有Activity
	 */
	protected void destroyActivityAll() {
		for (Activity act : activityList) {
			act.finish();
		}
	}
}
