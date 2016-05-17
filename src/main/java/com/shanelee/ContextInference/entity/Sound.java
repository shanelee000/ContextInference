package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Sound implements IAttributes{

    VERY_LOUD("very loud"),
    LOUD("loud"),
    NORMAL("normal"),
    QUIET("quiet"),
    VERY_QUEIT("very quiet");

    private String name;

    Sound(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
