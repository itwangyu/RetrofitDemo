package com.retrofit.retrofitdemo;

import com.retrofit.retrofitdemo.bean.CmsEntity;
import com.retrofit.retrofitdemo.bean.Login;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by WangYu on 2017/7/19.
 */

public interface ApiService {
    @POST("login/index")
    @FormUrlEncoded
    Flowable<Login> postLogin(@Field("account") String username, @Field("pwd") String pwd, @Field("accessPort") int prot, @Field("version") String version);

    @GET("appcms/cmsunlogin")
    Flowable<CmsEntity> getInfo();
}
