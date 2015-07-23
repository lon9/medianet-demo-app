package com.example.rompei.medianet_demo_app.bgm.api;

import com.example.rompei.medianet_demo_app.bgm.models.ThreadEntity;

import javax.security.auth.callback.Callback;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by rompei on 2015/07/24.
 */
public interface ThreadApi {
    @GET("/api/bbs.php")
    Observable<ThreadEntity> get();

    @POST("/api/bbs.php")
    Observable<ThreadEntity> post(@Body ThreadEntity.Thread thread, retrofit.Callback<ThreadEntity.Thread> cb);
}
