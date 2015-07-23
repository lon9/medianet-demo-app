package com.example.rompei.medianet_demo_app.api.models;

import java.util.List;

/**
 * Created by rompei on 2015/07/23.
 */
public class ThreadEntity {

    public List<Thread> threads;

    public class Thread{
        public String name;
        public String text;
    }
}
