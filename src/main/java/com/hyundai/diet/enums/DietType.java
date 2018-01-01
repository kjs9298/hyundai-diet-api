package com.hyundai.diet.enums;

import org.apache.commons.codec.binary.StringUtils;

public enum DietType {
    BREAKFAST("조식"),
    LUNCH("중식"),
    DINNER("석식");

    private String desc;

    DietType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;

    }

    public static DietType getFromDesc(String desc) {
        for(DietType dietType : DietType.values()) {
            if(StringUtils.equals(desc, dietType.getDesc())) {
                return dietType;

            }

        }
        return null;

    }
}
