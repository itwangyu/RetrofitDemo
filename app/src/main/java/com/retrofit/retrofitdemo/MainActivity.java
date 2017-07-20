package com.retrofit.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.retrofit.retrofitdemo.http.RetrofitUtil;

import java.util.ArrayList;

import io.reactivex.Flowable;

/**
 * Created by WangYu on 2017/7/19.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "wangyu";
    private TextView textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> get2());
        textview.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,MainActivity.class)));
        /**
         * 操作符笔记
         * map:对数据进行转换
         * flatmap:与map的不同之处在于他的返回值是Flowable,这样就可以对接收到的数据进行原始的操作.而不单单是改变数据本身.
         *          其实这个操作可以用来减少嵌套从而实现流式调用,假如list是从服务器获取的,那么我们就不用在subscribe里面再写议程
         *          Flowable.fromIterable.... 通过flet可以将数据list直接变成Flowable向下传递
         * filter:过滤数据,返回false表示拦截
         * take:指定订阅者最多接收多少数据
         * doOnNext:在subscribe之前对每个数据进行处理
         */
    }

    /**
     * 一个基本的请求
     */
    private void getinfo() {
        RetrofitUtil.getApiService(this)
                .getInfo()
                .compose(RetrofitUtil.getComposer())
                .subscribe(login ->
                {
                    Log.i(TAG, "login: 接收到结果");
                    textview.setText(login.toString());
                });
    }

    /**
     * 示范了对返回数据的简单处理
     */
    public void get2() {
        ArrayList<String> imageUrlList = new ArrayList<>();
        RetrofitUtil.getApiService(this)//如果需要显示加载对话框就传入当前activity,否则不传
                 .getInfo()
                .compose(RetrofitUtil.getComposer())
                //这里可以进行rxjava的各种操作比如返回list的时候我们进行遍历,将所有图片的地址添加到一个集合里面
                //flatMap遍历发射
                .flatMap(cmsEntity -> Flowable.fromIterable(cmsEntity.obj.bannerList))
                //收到之后添加
                .doOnNext(imageVo -> imageUrlList.add(imageVo.imageSrc))
                //最后显示
                .subscribe(image ->textview.setText(imageUrlList.toString()));
    }
}
