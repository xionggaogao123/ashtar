package com.ashstr.spider.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author keven
 * @date 2017-12-15 下午1:48
 * @Description
 */
public enum SpiderArticleTypeEnums {

    NEIHAN(1, "内涵社区"),

    BUDEJIE(2, "不得姐"),

    FANJIAN(3, "犯贱"),

    PENGFU(4, "捧腹");

    @Getter
    private final int value;

    @Getter
    private final String desc;

    SpiderArticleTypeEnums(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SpiderArticleTypeEnums from(int value) {
        for (SpiderArticleTypeEnums source : SpiderArticleTypeEnums.values()) {
            if (Objects.equals(source.value, value)) {
                return source;
            }
        }
        return null;
    }

}
