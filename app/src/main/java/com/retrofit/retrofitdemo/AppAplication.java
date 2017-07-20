package com.retrofit.retrofitdemo;

import android.app.Application;
import android.content.Context;

import com.retrofit.retrofitdemo.http.RetrofitUtil;

/**
 * Created by WangYu on 2017/7/20.
 */

public class AppAplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        RetrofitUtil.getApiService();
    }
}
