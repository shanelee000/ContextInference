package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Context implements IAttributes{
    RUNNING("running"),
    WALKING("walking"),
    IDLE("idle"),
    RESTING("resting");

    private String name;

    @Override
    public String getName() {
        return name;
    }

    Context(String name) {

        this.name = name;
    }
}
