package com.hyundai.diet.domain;

import java.util.List;

public class Keyboard {
    private String type;
    private List<String> buttons;

    public Keyboard(String type, List<String> buttons) {
        this.type = type;
        this.buttons = buttons;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getButtons() {
        return buttons;
    }

    public void setButtons(List<String> buttons) {
        this.buttons = buttons;
    }

}
