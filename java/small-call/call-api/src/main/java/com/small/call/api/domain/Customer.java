package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午3:31
 * @Description 客户
 */
@Data
public class Customer implements Serializable{

    private static final long serialVersionUID = 7837464069928154777L;


    private Long id;

    /**
     * 所属组
     */
    private Long groupId;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 描述
     */
    private String desc;

    private Date createTime;

    private Date updateTime;
}
