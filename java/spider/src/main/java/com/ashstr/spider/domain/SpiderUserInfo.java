package com.ashstr.spider.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-08 下午4:16
 * @Description 爬 用户的信息
 */
@Data
public class SpiderUserInfo implements Serializable{

    private static final long serialVersionUID = -5110918966587056545L;

    private Long id;

    /**
     * 联系人
     */
    private String name;

    /**
     * 电话号码
     */
    private String telPhone;


    /**
     * 地址
     */
    private String address;

    /**
     * 公司名称
     */
    private String companyName;


    /**
     * 主营产品
     */
    private String mainProduct;

    /**
     * 来源
     */
    private Integer channel;

    /**
     * 关键字
     */
    private String keyword;

    private Date createTime;

    private Date updateTime;

}
