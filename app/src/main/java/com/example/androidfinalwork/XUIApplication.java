package com.example.androidfinalwork;

import android.app.Application;

import com.xuexiang.xui.XUI;

public class XUIApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
    }
}
