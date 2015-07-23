package com.example.rompei.medianet_demo_app.dummy.models;

import java.util.List;

/**
 * Created by rompei on 2015/07/23.
 */
public class DummyEntity {
    public String base;
    public List<Weather> weather;

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }
}
