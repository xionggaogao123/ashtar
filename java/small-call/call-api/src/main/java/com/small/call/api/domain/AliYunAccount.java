package com.small.call.api.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午11:49
 * @Description
 */
@Data
public class AliYunAccount implements Serializable{

    private static final long serialVersionUID = -1510390820586569944L;


    private Long id;

    private String accessKeyId;

    private String accessKeySecret;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
