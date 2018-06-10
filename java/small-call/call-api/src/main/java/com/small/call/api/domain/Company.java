package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午3:21
 * @Description  公司 实体类
 */
@Data
public class Company implements Serializable{

    private static final long serialVersionUID = -8384891162055499896L;

    private Long id;

    /**
     * 上级代理id
     */
    private Long pid;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 类型 0 普通客户  1 代理商
     */
    private Integer type;


    private Date createTime;

    private Date updateTime;
}
