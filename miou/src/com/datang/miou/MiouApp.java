package com.datang.miou;

import android.app.Application;

/**
 * @author suntongwei
 */
public class MiouApp extends Application {

    public static MiouApp AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = this;
    }
}
