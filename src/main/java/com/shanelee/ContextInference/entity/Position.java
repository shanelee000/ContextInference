package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Position {
    LYING("lying"),
    STANDING("standing");

    private String name;

    public String getName() {
        return name;
    }

    Position(String name) {

        this.name = name;
    }
}
