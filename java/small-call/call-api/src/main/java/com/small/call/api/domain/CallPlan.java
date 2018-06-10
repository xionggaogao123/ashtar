package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午3:34
 * @Description 打电话计划 实体类
 */
@Data
public class CallPlan implements Serializable{

    private static final long serialVersionUID = 2315158708993868132L;


    private Long id;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 客户组Id
     */
    private Long groupId;

    /**
     * 客户组名称
     */
    private String groupName;

    /**
     * 话术id
     */
    private Long topicId;

    /**
     * 话术名称
     */
    private String topicName;

    /**
     * 通道Id
     */
    private Long gatewayId;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 日呼出上限
     */
    private Integer limitation;

    /**
     * 失败重试间隔，单位 分钟
     */
    private Integer retryInterval;

    /**
     * 失败 重试 次数
     */
    private Integer retryMax;

    /**
     * 节假日休息 1 表示休息 0 表示不休息
     */
    private Integer holidayRest;


    /**
     * 状态
     */
    private Integer status;

    /**
     * 呼出时间段
     */
    private String timeSlot;

    private Date createTime;

    private Date updateTime;

}
