package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Humidity implements IAttributes{
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private String name;

    @Override
    public String getName() {
        return name;
    }

    Humidity(String name) {

        this.name = name;
    }
}
