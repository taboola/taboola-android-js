package com.taboola.samples.js;

import android.app.Application;

import com.taboola.android.js.TaboolaJs;

public class JsSampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TaboolaJs.getInstance().init(getApplicationContext());
    }
}
