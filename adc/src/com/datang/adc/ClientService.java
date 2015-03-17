package com.datang.adc;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import com.datang.adc.util.Util;

public class ClientService extends Service {
    public static final ClientManager MANAGER = ClientManager.getManager();
    public static final String CLIENT = "com.datang.client";
    private IBinder localBinder = new LocalBinder();
    private String curBoxid="";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(CLIENT)){
            Uri uri = intent.getData();
            String scheme = uri.getScheme();
            if (!Util.isEmpty(scheme) && scheme.equalsIgnoreCase("login")) {
                IClient client = MANAGER.register(uri.getHost(), uri.getPort());
                curBoxid = uri.getQueryParameter("boxid");
                MANAGER.setOption(curBoxid,uri.getQueryParameter("sver"),uri.getQueryParameter("cver"));
                client.connect();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // 定义内容类继承Binder
    public class LocalBinder extends Binder {
        // 返回本地服务
        public ClientService getService() {
            return ClientService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

}
