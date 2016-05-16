package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Temperature {

    VERY_HOT("very hot"),
    HOT("hot"),
    NORMAL("normal"),
    COLD("cold"),
    VERY_COLD("very cold");


    private String name;

    public String getName() {
        return name;
    }

    Temperature(String name) {

        this.name = name;
    }
}
