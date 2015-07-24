package com.example.rompei.medianet_demo_app.bgm.models;

import java.util.List;

/**
 * Created by rompei on 2015/07/23.
 */
public class ThreadEntity {

    public List<Reply> reply;

    public static class Reply {
        public String name;
        public String text;
        public Reply(String name, String text){
            this.name=name;
            this.text=text;
        }
    }
}
