package com.datang.miou;

import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

/**
 * 
 * 
 * @author suntongwei
 */
public abstract class BaiduMapActivity extends ActivitySupport {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SDKInitializer.initialize(getApplicationContext());
		super.onCreate(savedInstanceState);
	}
	
	public abstract MapView getMapView();
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(getMapView() != null) {
			getMapView().onDestroy();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(getMapView() != null) {
			getMapView().onResume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(getMapView() != null) {
			getMapView().onPause();
		}
	}
}
