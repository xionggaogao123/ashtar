package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午3:28
 * @Description 客户组
 */
@Data
public class CustomerGroup implements Serializable{

    private static final long serialVersionUID = 6888228668693911479L;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 客户组名称
     */
    private String groupName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户组所包含的客户数量
     */
    private Integer quantity;


    private Date createTime;


    private Date updateTime;

}
