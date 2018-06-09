package com.ashstr.spider.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2017-12-15 下午1:35
 * @Description 爬虫游戏与广告主关系实体类
 */
@Data
public class SpiderGameAdvertiserRel implements Serializable{

    private static final long serialVersionUID = -5908824722749601153L;

    private Long id;

    private Date createTime;

    private Long gameInfoId;

    private Integer gameId;

    private Long advertiserInfoId;

    private String advertiserLink;
}
