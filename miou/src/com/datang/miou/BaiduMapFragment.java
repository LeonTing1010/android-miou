package com.datang.miou;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 
 * @author suntongwei
 */
public abstract class BaiduMapFragment extends FragmentSupport {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract MapView getMapView();
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(getMapView() != null) {
			getMapView().onDestroy();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(getMapView() != null) {
			getMapView().onResume();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(getMapView() != null) {
			getMapView().onPause();
		}
	}
}
