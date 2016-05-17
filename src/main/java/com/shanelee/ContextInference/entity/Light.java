package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/15.
 */
public enum Light implements IAttributes{

    VERY_BRIGHT("very_bright"),
    BRIGHT("bright"),
    NORMAL("normal"),
    DARK("dark"),
    VERY_DARK("very dark");

    private String name;

    Light(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
