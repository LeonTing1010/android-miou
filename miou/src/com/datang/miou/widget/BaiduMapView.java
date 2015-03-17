package com.datang.miou.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.MapView;
import com.datang.miou.R;

/**
 * 
 * 
 * @author suntongwei
 */
public class BaiduMapView extends RelativeLayout {

	// 百度地图View
	protected MapView mMapView;
	
	public BaiduMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.map, this, true);
		mMapView = (MapView) findViewById(R.id.bmap_view);
	}

	public MapView getMapView() {
		return mMapView;
	}
	
}
