package com.retrofit.retrofitdemo.http;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.retrofit.retrofitdemo.ApiService;
import com.retrofit.retrofitdemo.AppAplication;
import com.retrofit.retrofitdemo.bean.BaseBean;
import com.retrofit.retrofitdemo.util.ApkUtils;

import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 * Created by WangYu on 2017/7/20.
 */

public class RetrofitUtil {
    public static final String TAG = "wangyu";
    public static Gson gson;
    public static Retrofit retrofit;
    public static OkHttpClient.Builder client;
    private static AlertDialog alertDialog;
    public static Activity context;
    private static Subscription sub;

    public static ApiService getApiService(Activity activity) {
        context = activity;
        return getApiService();
    }

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(getGsonInstance()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpInstance())
                    .baseUrl("http://app.ddshenbian.com/").build();
        }

        return retrofit.create(ApiService.class);
    }

    public static void showDialog(Activity activity) {
        alertDialog = new AlertDialog.Builder(activity)
                .setTitle("标题")
                .setMessage("这是一个加载对话框")
                .setOnCancelListener(dialogInterface -> {
                    //在取消对话框的时候同时取消网络请求
                    dialogInterface.dismiss();
                    if (sub != null) {
                       sub.cancel();
                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /**
     * 实例化okhttp,log打印,添加公共参数,cookie保持,缓存策略,超时和重连
     *
     * @return
     */
    public static OkHttpClient getOkHttpInstance() {
        if (client == null) {
            client = new OkHttpClient.Builder();
            /**
             * log信息拦截器
             */
            //log信息拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置Debug Log模式
            client.addInterceptor(httpLoggingInterceptor);
            /**
             * 缓存
             */
            final File chachefile = new File(AppAplication.getContext().getExternalCacheDir(), "HttpCache");
            final Cache cache = new Cache(chachefile, 1024 * 1024 * 50);//缓存文件
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!ApkUtils.isNetworkAvailable(AppAplication.getContext())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (ApkUtils.isNetworkAvailable(AppAplication.getContext())) {
                        int maxAge = 0;

                        // 有网络时 设置缓存超时时间0个小时
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    } else {
                        //无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("Pragma")
                                .build();
                    }

                    return response;
                }
            };
//            client.cache(cache).addInterceptor(cacheInterceptor);
            /**
             * 公共参数
             */
            Interceptor addQueryParameterInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request;
                    String method = originalRequest.method();
                    Headers headers = originalRequest.headers();
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            // Provide your custom parameter here
                            .addQueryParameter("accessPort", "2")
                            .addQueryParameter("version", "1.0")
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();
                    return chain.proceed(request);
                }
            };
            //公共参数
            client.addInterceptor(addQueryParameterInterceptor);
            /**
             * 设置头
             */
            Interceptor headerInterceptor = new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request orignaRequest = chain.request();
                    Request request = orignaRequest.newBuilder()
                            .header("AppType", "TPOS")
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .method(orignaRequest.method(), orignaRequest.body())
                            .build();

                    return chain.proceed(request);
                }
            };
            client.addInterceptor(headerInterceptor);
            /**
             * 设置cookie
             */
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            client.cookieJar(new JavaNetCookieJar(cookieManager));
            /**
             * 设置超时和重连
             */
            //设置超时
            client.connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    //错误重连
                    .retryOnConnectionFailure(true);
        }
        return client.build();
    }

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder()
                    //配置你的Gson
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
        }
        return gson;
    }

    /**
     * 这个方法里可以进行返回信息的统一处理
     * @param <T>
     * @return
     */
    public static <T extends BaseBean> FlowableTransformer<T, T> getComposer() {
        FlowableTransformer flowableTransformer = (FlowableTransformer<T, T>) upstream -> {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(t -> {
                        if (t.code == -99) {
                            //TODO 打开登录页面
                            Log.i(TAG, "test: 打开登录页面");
                            return false;
                        }
                        if (t.code != 1) {
                            // TODO 返回错误信息
                            Log.i(TAG, "test: code" + t.code);
                            return false;
                        }
                        //返回flse代表拦截,返回结果不会传递到subScribe中去,true代表不拦截
                        return true;
                    })
                    .doOnSubscribe(subscription -> {
                        //  TODO 在这里弹出加载进度dialog
                        sub=subscription;
                        if (context != null) {
                            showDialog(context);
                        }
                    })
                    .doOnComplete(() -> {
                        dismissDialog();
                        Log.i(TAG, "onComplete: ");
                    })
                    .doOnError(throwable -> {
                                Log.i(TAG, "获取失败: ");
                                dismissDialog();
                    })
                    .doOnCancel(() -> {
                        Log.i(TAG, "取消订阅: ");
                        dismissDialog();
                    });
        };
        return flowableTransformer;
    }
}
