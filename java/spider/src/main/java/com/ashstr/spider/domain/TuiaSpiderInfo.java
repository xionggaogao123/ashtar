package com.ashstr.spider.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2017-12-15 下午1:45
 * @Description
 */
@Data
public class TuiaSpiderInfo implements Serializable{

    private static final long serialVersionUID = 1943307969954468473L;

    private Date createTime;

    private Integer slotId;

    private Long gameId;

    private String gameLink;

    private String imageUrl;

    private String gameType;

    private String advertiserLink;

    private String advertiserRemark;

    private String advertiserParentType;

    private String advertiserType;

    private String appName;

    private Integer countNum;

    private String createTimeStr;

    private Long advertiserInfoId;
}
