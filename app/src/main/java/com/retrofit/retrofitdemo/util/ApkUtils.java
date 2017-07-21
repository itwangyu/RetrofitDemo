package com.retrofit.retrofitdemo.util;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.retrofit.retrofitdemo.R;

/**
 * Created by WangYu on 2017/7/20.
 */

public class ApkUtils {

    private static Dialog loadingDialog;
    private static Context oldContext;

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progress, null);// 得到加载view
        CircleProgressBar cpb = (CircleProgressBar) v.findViewById(R.id.progressBar);
        if (oldContext == null || !oldContext.toString().equals(context.toString())) {
            loadingDialog = new Dialog(context, R.style.loading_dialog);
        }
        oldContext = context;

        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                dip2px(context, 100), dip2px(context, 100)));// 设置布局
        return loadingDialog;

    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
