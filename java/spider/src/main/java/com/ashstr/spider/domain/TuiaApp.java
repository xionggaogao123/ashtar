package com.ashstr.spider.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author keven
 * @date 2017-12-15 下午1:44
 * @Description
 */
@Data
@AllArgsConstructor
public class TuiaApp implements Serializable{

    private static final long serialVersionUID = 575673670472270535L;

    /**
     *
     */
    private String appKey;

    /**
     *
     */
    private String appName;

    /**
     *
     */
    private Integer appSlotId;

}
