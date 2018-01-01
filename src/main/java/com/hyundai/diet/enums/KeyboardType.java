package com.hyundai.diet.enums;

public enum KeyboardType {
    BUTTON("buttons"),
    TEXT("text");

    private String value;

    KeyboardType(String value) {
        this.value = value;

    }

    public String getValue() {
        return this.value;

    }
}
