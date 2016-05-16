package com.shanelee.ContextInference.entity;

/**
 * Created by ShaneLee on 16/5/17.
 */
public enum Attributes {
    LIGHT("light"),
    SOUND("sound"),
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    POSITION("position"),
    MOVEMENT("movement"),
    GPS("gps"),
    TIME("time");

    private String attrName;

    public String getAttrName() {
        return attrName;
    }

    Attributes(String attrName) {

        this.attrName = attrName;
    }
}
