package com.hyundai.diet.enums;

import org.apache.commons.codec.binary.StringUtils;

public enum DietSubType {
    BREAKFAST_KOREAN("한식"),
    BREAKFAST_SALAD("샐러드"),
    BREAKFAST_RAMEN("라면"),
    BREAKFAST_BREAD("빵"),
    COURSE_A("A코스"),
    COURSE_B("B코스"),
    DESSERT("후식");

    private String desc;

    DietSubType(String desc) {
        this.desc = desc;

    }

    public String getDesc() {
        return this.desc;

    }

    public static DietSubType getFromDesc(String desc) {
        for(DietSubType subType : DietSubType.values()) {
            if(StringUtils.equals(desc, subType.getDesc())) {
                return subType;

            }

        }
        return null;

    }

}
