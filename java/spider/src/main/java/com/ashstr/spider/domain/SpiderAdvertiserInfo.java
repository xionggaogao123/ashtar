package com.ashstr.spider.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2017-12-15 下午1:27
 * @Description 爬虫广告主信息实体类，属性的顺序是有问题的，为了兼容，先这样吧。
 */
@Data
public class SpiderAdvertiserInfo implements Serializable{

    private static final long serialVersionUID = 2768748251018548106L;

    private Long id;

    private Date createTime;

    private Integer slotId;

    private String advertiserLink;

    private String advertiserRemark;

    private String appName;

    private String imageUrl;

    /**
     * 广告主一级行业类型
     */
    public String getAdvertiserParentType() {
        String adType = getAdvertiserType();
        if ("理财".equals(adType) || "股票".equals(adType) || "贷款".equals(adType) || "保险".equals(adType) || "信用卡".equals(adType) || "P2P".equals(adType) || "股票基金".equals(adType)) {
            return "金融";
        } else if ("教育".equals(adType)) {
            return "教育";
        } else if ("旅游".equals(adType)) {
            return "旅游";
        } else if ("生活服务".equals(adType) || "装修".equals(adType) || "婚恋交友".equals(adType)) {
            return "生活服务";
        } else if ("交友".equals(adType)) {
            return "娱乐";
        } else if ("兼职".equals(adType)) {
            return "生活服务";
        } else if ("女性".equals(adType)) {
            return "健康";
        } else if ("夺宝游戏".equals(adType) || "棋牌游戏".equals(adType) || "博彩".equals(adType)) {
            return "游戏";
        } else if ("平台电商".equals(adType) || "二类电商".equals(adType)) {
            return "电商";
        } else if ("独立电商".equals(adType) || "二类电商".equals(adType)) {
            return "电子商务";
        } else if ("求职招聘".equals(adType)) {
            return "商务服务";
        }
        return "";
    }

    /**
     * 广告主二级行业类型
     */
    public String getAdvertiserType() {

        if (advertiserRemark.contains("投资") || advertiserRemark.contains("P2P") || advertiserRemark.contains("收益") || advertiserRemark.contains("基金") || advertiserRemark.contains("回报") || advertiserRemark.contains("理财")
                ) {
            return "理财";
        } else if (advertiserRemark.contains("妖股") || advertiserRemark.contains("牛股")) {
            return "股票";
        } else if (advertiserRemark.contains("借款") || advertiserRemark.contains("借条") || advertiserRemark.contains("放贷") || advertiserRemark.contains("借款") || advertiserRemark.contains("借条") || advertiserRemark.contains("贷款") || advertiserRemark.contains("卡债省钱利器") || advertiserRemark.contains("放贷") || advertiserRemark.contains("借款") || advertiserRemark.contains("借") || advertiserRemark.contains("免息借") || advertiserRemark.contains("手机借钱")) {
            return "贷款";
        } else if (advertiserRemark.contains("赠险") || advertiserRemark.contains("保障") || advertiserRemark.contains("意外保险") || advertiserRemark.contains("疾病险") || advertiserRemark.contains("太平人寿") || advertiserRemark.contains("出行险") || advertiserRemark.contains("中国平安") || advertiserRemark.contains("太平人寿") || advertiserRemark.contains("出行") || advertiserRemark.contains("保险") || advertiserRemark.contains("意外保障") || advertiserRemark.contains("意外险") || advertiserRemark.contains("出行险")) {
            return "保险";
        } else if (advertiserRemark.contains("信用") || advertiserRemark.contains("平安普惠") || advertiserRemark.contains("额度") || advertiserRemark.contains("发卡") || advertiserRemark.contains("借钱") || advertiserRemark.contains("高端会籍卡") || advertiserRemark.contains("刷卡") || advertiserRemark.contains("金卡") || advertiserRemark.contains("兴业银行信用卡") || advertiserRemark.contains("高额度信用卡") || advertiserRemark.contains("金卡") || advertiserRemark.contains("额度") || advertiserRemark.contains("黑卡") || advertiserRemark.contains("端会籍卡") || advertiserRemark.contains("额度")) {
            return "信用卡";
        } else if (advertiserRemark.contains("英语") || advertiserRemark.contains("记单词") || advertiserRemark.contains("英文交际")) {
            return "教育";
        } else if (advertiserRemark.contains("遨游大师")) {
            return "旅游";
        } else if (advertiserRemark.contains("装修")) {
            return "生活服务";

        } else if (advertiserRemark.contains("单身美女") || advertiserRemark.contains("不嫌弃就约") || advertiserRemark.contains("约会") || advertiserRemark.contains("小姐姐")) {
            return "交友";

        } else if (advertiserRemark.contains("赚房租")) {
            return "兼职";

        } else if (advertiserRemark.contains("减肥") || advertiserRemark.contains("狐臭") || advertiserRemark.contains("祛斑")) {
            return "女性";

        } else if (advertiserRemark.contains("起拍") || advertiserRemark.contains("夺宝") || advertiserRemark.contains("iPhone") || advertiserRemark.contains("1元") || advertiserRemark.contains("一元") || advertiserRemark.contains("Apple") || advertiserRemark.contains("0元起拍") || advertiserRemark.contains("一元抢") || advertiserRemark.contains("花1元钱")) {
            return "夺宝游戏";
        } else if (advertiserRemark.contains("游戏") || advertiserRemark.contains("捕鱼") || advertiserRemark.contains("注册就送") || advertiserRemark.contains("扎金花") || advertiserRemark.contains("德州") || advertiserRemark.contains("牌技") || advertiserRemark.contains("免费试玩") || advertiserRemark.contains("棋牌") || advertiserRemark.contains("免费玩") || advertiserRemark.contains("金币天天送") || advertiserRemark.contains("斗地主") || advertiserRemark.contains("中了算你的") || advertiserRemark.contains("电玩") || advertiserRemark.contains("彩票") || advertiserRemark.contains("我的澳门") || advertiserRemark.contains("捕鱼") || advertiserRemark.contains("银币") || advertiserRemark.contains("金币") || advertiserRemark.contains("竞猜游戏")) {
            return "棋牌游戏";
        } else if (advertiserRemark.contains("彩票") || advertiserRemark.contains("足球") || advertiserRemark.contains("彩金券")) {
            return "博彩";
        } else if (advertiserRemark.contains("聚美优品") || advertiserRemark.contains("每日优鲜") || advertiserRemark.contains("造作") || advertiserRemark.contains("网易严选") || advertiserRemark.contains("美啦")) {
            return "平台电商";
        } else if (advertiserRemark.contains("抢购") || advertiserRemark.contains("看片神器") || advertiserRemark.contains("大促") || advertiserRemark.contains("今日特惠") || advertiserRemark.contains("表") || advertiserRemark.contains("箱") || advertiserRemark.contains("耳机") || advertiserRemark.contains("充电") || advertiserRemark.contains("手机") || advertiserRemark.contains("专柜") || advertiserRemark.contains("内裤") || advertiserRemark.contains("进口黑啤") || advertiserRemark.contains("卫裤") || advertiserRemark.contains("佛") || advertiserRemark.contains("蓝牙耳机") || advertiserRemark.contains("手表") || advertiserRemark.contains("充电宝") || advertiserRemark.contains("雨伞")) {
            return "二类电商";
        } else if (advertiserRemark.contains("约会神器")) {
            return "婚恋交友";
        } else if (advertiserRemark.contains("每日优鲜") || advertiserRemark.contains("满99减80优惠券") || advertiserRemark.contains("聚美") || advertiserRemark.contains("网易严选")) {
            return "独立电商";
        } else if (advertiserRemark.contains("火币网") || advertiserRemark.contains("医界贷") || advertiserRemark.contains("叮咚钱包") || advertiserRemark.contains("理财") || advertiserRemark.contains("理财") || advertiserRemark.contains("年化收益") || advertiserRemark.contains("收益不封顶") || advertiserRemark.contains("投资即可提现")) {
            return "P2P";
        } else if (advertiserRemark.contains("基金")) {
            return "股票基金";
        } else if (advertiserRemark.contains("玩微信")) {
            return "求职招聘";
        }
        return "";
    }
}
