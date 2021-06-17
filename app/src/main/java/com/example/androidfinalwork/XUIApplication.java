package com.example.androidfinalwork;

import android.app.Application;

import com.xuexiang.xui.XUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XUIApplication extends Application {
    public Map<String,Integer> user;
    public Map<String,Integer> xiaoIce;
    public int chatCount;
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
        user = new HashMap<String, Integer>();
        user.put("like",0);
        user.put("happy",0);
        user.put("angry",0);
        user.put("sad",0);
        user.put("fearful",0);
        xiaoIce = new HashMap<String, Integer>();
        xiaoIce.put("like",0);
        xiaoIce.put("happy",0);
        xiaoIce.put("angry",0);
        xiaoIce.put("sad",0);
        xiaoIce.put("fearful",0);
        chatCount=0;
    }
}
