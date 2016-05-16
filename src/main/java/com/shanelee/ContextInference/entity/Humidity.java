package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Humidity {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private String name;

    public String getName() {
        return name;
    }

    Humidity(String name) {

        this.name = name;
    }
}
