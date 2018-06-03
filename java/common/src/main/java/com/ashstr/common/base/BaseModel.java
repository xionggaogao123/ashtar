package com.ashstr.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-02 下午10:47
 * @Description 实体 基类
 */
@Data
public class BaseModel implements Serializable{

    private static final long serialVersionUID = 1549023330744296852L;

    private Long id;

    private Date createTime;

    private Date updateTime;

}
