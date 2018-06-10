package com.small.call.api.domain;


import java.io.Serializable;

/**
 * @author keven
 * @date 2018-06-10 下午4:17
 * @Description 用户 实体类
 */
public class User implements Serializable{

    private static final long serialVersionUID = -4831091166861231207L;

    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属 公司id
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;


    /**
     * 状态
     */
    private Integer status;

    /**
     * 类型
     */
    private Integer type;


}
