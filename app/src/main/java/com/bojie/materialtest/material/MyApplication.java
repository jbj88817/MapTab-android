package com.bojie.materialtest.material;

import android.app.Application;
import android.content.Context;

/**
 * Created by bojiejiang on 5/2/15.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
    public static final String API_KEY_ROTTEN_TOMATOES = "e4hvtyb5vhpd7wq24kj4dn35";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
