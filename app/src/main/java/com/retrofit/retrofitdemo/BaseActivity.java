package com.retrofit.retrofitdemo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.retrofit.retrofitdemo.util.ApkUtils;

import org.reactivestreams.Subscription;

/**
 * Created by WangYu on 2017/7/20.
 */

public class BaseActivity extends AppCompatActivity{
    private static Dialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = ApkUtils.createLoadingDialog(this);
    }

    public  void showDialog(Subscription subscription) {
        if (loadingDialog != null) {
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    subscription.cancel();
                }
            });
            loadingDialog.show();
        }
    }

    public  void dismissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
