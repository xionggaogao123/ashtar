package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午4:03
 * @Description 通道 实体类
 */
@Data
public class Gateway implements Serializable{


    private static final long serialVersionUID = -654683642659604797L;


    private Long id;

    /**
     * 对应的号码
     */
    private String phoneNum;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 设备系列号
     */
    private String serialNum;

    /**
     * 卡所在设备里 连接的 SIP 服务器 ip
     */
    private String serverHost;

    /**
     * 卡 所在 设备里 连接的 SIP 服务器端口
     */
    private String serverPort;

    /**
     * 状态  0 正常  1 非正常
     */
    private Integer status;

    /**
     * 服务开通时间
     */
    private Date openTime;


    /**
     * 服务到期时间
     */
    private Date expireTime;

    /**
     * 通道 连接 密码
     */
    private String password;


    /**
     * 备注
     */
    private String desc;


    private Date createTime;


    private Date updateTime;

}
