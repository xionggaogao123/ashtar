package com.small.call.api.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午4:50
 * @Description 话术 详情表
 */
@Data
public class DetailTopic implements Serializable{

    private static final long serialVersionUID = 7673498922955689708L;


    private Long id;

    /**
     * 话术id
     */
    private Long topicId;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 流程话术下，前一个对话的id
     */
    private Long frontId;


    /**
     * 肯定回复状态
     */
    private Integer sure;

    /**
     * 确认回答下级话术Id
     */
    private Long sureId;

    /**
     * 否定回复状态
     */
    private Integer deny;

    /**
     * 否定回答下级 话术id
     */
    private Long denyId;


    /**
     * 类型  ： 0 流程话术语  1 关键词 话术语
     */
    private Integer type;


    /**
     * 状态
     */
    private Integer status;

    /**
     * 关键词 回答模式下，关键词列表
     */
    private String keyword;


    /**
     * 语音 存放地址
     */
    private String recordPath;

    /**
     * 音频文件长度，单位 毫秒
     */
    private Integer topicTime;

    /**
     * 备注
     */
    private String desc;

    private Date createTime;


    private Date updateTime;


}
