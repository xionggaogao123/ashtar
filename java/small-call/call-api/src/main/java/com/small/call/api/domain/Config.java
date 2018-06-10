package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午4:46
 * @Description 系统配置表
 */
@Data
public class Config implements Serializable{

    private static final long serialVersionUID = -2951573171189696879L;


    private Long id;

    /**
     * 系统配置 key
     */
    private String key;

    /**
     * key  对应的 value 值
     */
    private String value;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String desc;

    private Date createTime;

    private Date updateTime;
}
