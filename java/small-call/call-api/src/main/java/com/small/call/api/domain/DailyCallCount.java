package com.small.call.api.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午4:39
 * @Description 每日 call 统计表
 */
@Data
public class DailyCallCount implements Serializable{


    private static final long serialVersionUID = -5482752026288406497L;

    private Long id;

    /**
     * 计划Id
     */
    private Long planId;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 客户组Id
     */
    private Long groupId;


    /**
     * 客户组名称
     */
    private String groupName;


    /**
     * 话术 id
     */
    private Long topicId;

    /**
     * 话术 名称
     */
    private String topicName;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 当日 呼出总量
     */
    private Integer quantity;

    /**
     * 接通量
     */
    private Integer connect;

    /**
     * 意向客户量
     */
    private Integer intention;

    /**
     * 意向率
     */
    private Double rate;

    /**
     * 统计日期
     */
    private Date countTime;


    private Date createTime;

    private Date updateTime;


}
