package com.ashstr.spider.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2017-12-15 下午2:20
 * @Description 抓取 图片 实体类
 */
@Data
public class SpiderImage implements Serializable{

    private static final long serialVersionUID = 2483856472285697689L;

    private Long id;

    private String imgUrl;

    private Date createTime;

}
