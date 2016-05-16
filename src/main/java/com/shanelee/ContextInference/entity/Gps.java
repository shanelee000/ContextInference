package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Gps {
    INDOOR("indoor"),
    OUTDOOR("outdoor");

    private String name;

    public String getName() {
        return name;
    }

    Gps(String name) {

        this.name = name;
    }
}
