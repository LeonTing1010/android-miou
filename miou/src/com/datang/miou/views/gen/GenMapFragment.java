package com.datang.miou.views.gen;

import com.baidu.mapapi.map.MapView;
import com.datang.miou.BaiduMapFragment;
import com.datang.miou.R;
import com.datang.miou.annotation.AutoView;
import com.datang.miou.widget.BaiduMapView;

/**
 * 地图
 * 
 * @author suntongwei
 */
@AutoView(R.layout.gen_map)
public class GenMapFragment extends BaiduMapFragment {

	@AutoView(R.id.gen_map_view)
	private BaiduMapView baiduMapView;
	
	
	
	@Override
	public MapView getMapView() {
		return baiduMapView.getMapView();
	}

}
