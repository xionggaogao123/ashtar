package com.small.call.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2018-06-10 下午3:50
 * @Description
 */
@Data
public class Topic implements Serializable{

    private static final long serialVersionUID = 6928690811395612985L;

    private Long id;

    /**
     * 话术名称
     */
    private String name;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 流程话术打断 百分比
     */
    private Integer flowBreak;

    /**
     * 流程话术重复 百分比
     */
    private Integer flowRepeat;


    /**
     * 关键词 话术 打断 百分比
     */
    private Integer keywordBreak;

    /**
     * 关键词 话术 重复 百分比
     */
    private Integer keywordRepeat;

    /**
     * 场景 话术 打断 百分比
     */
    private Integer sceneBreak;

    /**
     * 场景 话术 重复 百分比
     */
    private Integer sceneRepeat;

    /**
     * A类意向匹配关键词数
     */
    private Integer intentionA;

    /**
     * A通话时长最小值
     */
    private Integer talkTimeMinA;

    /**
     * A通话时长最大值
     */
    private Integer talkTimeMaxA;

    /**
     * B类意向匹配关键词数
     */
    private Integer intentionB;

    /**
     * B 通话时长最小值
     */
    private Integer talkTimeMinB;

    /**
     * B通话时长最大值
     */
    private Integer talkTimeMaxB;

    /**
     * C类意向匹配关键词数
     */
    private Integer intentionC;

    /**
     * C通话时长最小值
     */
    private Integer talkTimeMinC;


    /**
     * C 通话时长最大值
     */
    private Integer talkTimeMaxC;

    /**
     * D类意向匹配关键词数
     */
    private Integer intentionD;

    /**
     * D 通话时长最小值
     */
    private Integer talkTimeMinD;

    /**
     * D 通话时长最大值
     */
    private Integer talkTimeMaxD;


    private Date createTime;

    private Date updateTime;

}
