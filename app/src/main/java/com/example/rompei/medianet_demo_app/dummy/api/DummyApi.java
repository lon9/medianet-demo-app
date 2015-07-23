package com.example.rompei.medianet_demo_app.dummy.api;

import com.example.rompei.medianet_demo_app.dummy.models.DummyEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rompei on 2015/07/23.
 */
public interface DummyApi {
    @GET("/data/2.5/{name}")
    public Observable<DummyEntity> get(@Path("name") String name, @Query("q") String q);
}
