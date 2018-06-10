package com.small.call.api.domain;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午4:22
 * @Description 打 call 日志详细数据 实体类
 */
@Data
public class CallDetailLog implements Serializable{

    private static final long serialVersionUID = 1070205325501846461L;

    private Long id;

    /**
     * 计划Id
     */
    private Long planId;


    /**
     * 计划名称
     */
    private Long planName;

    /**
     * 客户组id
     */
    private Long groupId;

    /**
     * 客户组名称
     */
    private String groupName;

    /**
     * 客户Id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户电话号码
     */
    private String phone;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 状态 0 呼叫中  1 失败 2 等待重试  3 无意向 4 意向
     */
    private Integer status;

    /**
     * 当前重试 次数
     */
    private Integer retryCount;


    /**
     * 通话时长
     */
    private Integer duration;

    /**
     * 录音文件 路径
     */
    private String recordPath;

    /**
     * 自定义 客户 等级
     */
    private Integer customerLevel;

    /**
     * 电话呼出 时间
     */
    private Date callTime;

    /**
     * 通话失败原因 code
     */
    private String failReason;

    /**
     * 备注
     */
    private String desc;


    private Date createTime;

    private Date updateTime;
}
