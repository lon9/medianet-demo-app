package com.example.rompei.medianet_demo_app.bgm.api;

import com.example.rompei.medianet_demo_app.bgm.models.ThreadEntity;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by rompei on 2015/07/24.
 */
public interface ThreadApi {
    @GET("/api/bbs.php")
    Observable<ThreadEntity> get();

    @POST("/api/bbs.php")
    Observable<ThreadEntity.Reply> post(@Body ThreadEntity.Reply thread);
}
