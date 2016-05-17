package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Position implements IAttributes{
    LYING("lying"),
    STANDING("standing");

    private String name;

    @Override
    public String getName() {
        return name;
    }

    Position(String name) {

        this.name = name;
    }
}
