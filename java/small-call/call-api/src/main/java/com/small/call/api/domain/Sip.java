package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午4:12
 * @Description sip 服务器 实体类
 */
@Data
public class Sip implements Serializable{


    private static final long serialVersionUID = 2755603725531824558L;

    private Long id;

    /**
     * 服务器ip 地址
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    private Date createTime;

    private Date updateTime;

}
