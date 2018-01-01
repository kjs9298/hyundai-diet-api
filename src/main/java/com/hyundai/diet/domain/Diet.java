package com.hyundai.diet.domain;

import org.apache.commons.lang3.StringUtils;

public class Diet {
    private String dietText;
    private int calorie;

    public Diet(String text) {
        String[] lines = text.split("\\r?\\n");

        dietText = "";
        for(String line : lines) {
            if(StringUtils.isNumeric(line)) {
                calorie = Integer.valueOf(line);

            } else {
                dietText += line;
                dietText += "\n";

            }

        }

    }

    public String getDietText() {
        return dietText;
    }

    public void setDietText(String dietText) {
        this.dietText = dietText;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }


}
