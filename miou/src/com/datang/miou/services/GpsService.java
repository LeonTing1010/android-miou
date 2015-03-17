package com.datang.miou.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.LocationClient;

/**
 * 
 * 
 * @author suntongwei
 */
public class GpsService extends Service {
	
	public LocationClient mLocationClient = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		
	}

}
