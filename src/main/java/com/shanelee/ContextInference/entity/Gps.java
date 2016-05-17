package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Gps implements IAttributes{
    INDOOR("indoor"),
    OUTDOOR("outdoor");

    private String name;

    @Override
    public String getName() {
        return name;
    }

    Gps(String name) {

        this.name = name;
    }
}
