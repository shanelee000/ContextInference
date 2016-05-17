package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Time implements IAttributes{
    MORNING("morning"),
    AFTERNOON("afternoon"),
    NIGHT("night");

    private String name;

    @Override
    public String getName() {
        return name;
    }

    Time(String name) {

        this.name = name;
    }
}
