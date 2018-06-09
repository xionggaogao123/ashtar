package com.ashstr.spider.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author keven
 * @date 2017-12-15 下午1:38
 * @Description
 */
@Data
public class SpiderGameInfo implements Serializable{

    private static final long serialVersionUID = 4011757890023435310L;

    private Long id;

    private Date createTime;

    private String agencySourceLink;

    private Integer gameId;

    private Integer slotId;

    private String gameLink;

    private String appName;

    public String getGameType() {
        int gameIdTemp = this.getGameId();
        if (gameIdTemp == 276 || gameIdTemp == 934) {
            return "拆快递";
        } else if (gameIdTemp == 248) {
            return "捞金鱼";
        } else if (gameIdTemp == 850 || gameIdTemp == 1386 || gameIdTemp == 1427) {
            return "扯红包-抽红包";
        } else if (gameIdTemp == 1581 || gameIdTemp == 1582 || gameIdTemp == 1700 || gameIdTemp == 1701) {
            return "抽签-抽红包";

        } else if (gameIdTemp == 457) {
            return "踹一脚-抽话费";

        } else if (gameIdTemp == 720) {
            return "大转盘-Q币";

        } else if (gameIdTemp == 518) {
            return "大转盘-王者荣耀";

        } else if (gameIdTemp == 923 || gameIdTemp ==
                999 || gameIdTemp ==
                1189 || gameIdTemp ==
                1387) {
            return "翻牌-抽红包";

        } else if (gameIdTemp == 80 || gameIdTemp ==
                1628) {
            return "割绳子";

        } else if (gameIdTemp == 114 || gameIdTemp == 193 || gameIdTemp == 197 || gameIdTemp == 202 || gameIdTemp == 227 || gameIdTemp == 238 || gameIdTemp == 415 || gameIdTemp == 458 || gameIdTemp == 494 || gameIdTemp == 552 || gameIdTemp == 612 || gameIdTemp == 619 || gameIdTemp == 664 || gameIdTemp == 700 || gameIdTemp == 805 || gameIdTemp == 853 || gameIdTemp == 870 || gameIdTemp == 962 || gameIdTemp == 998 || gameIdTemp == 1088 || gameIdTemp == 1159 || gameIdTemp == 1378 || gameIdTemp == 1577 || gameIdTemp == 225) {
            return "刮刮乐";

        } else if (gameIdTemp ==
                655) {
            return "刮刮乐-手机红包";

        } else if (gameIdTemp == 623 || gameIdTemp ==
                751 || gameIdTemp ==
                854 || gameIdTemp == 228) {
            return "夹娃娃";

        } else if (gameIdTemp == 632) {
            return "捞礼物-手机红包";

        } else if (gameIdTemp == 24 || gameIdTemp == 447 || gameIdTemp == 675 || gameIdTemp == 777 || gameIdTemp == 778 || gameIdTemp == 911 || gameIdTemp == 1034 || gameIdTemp == 1091 || gameIdTemp == 1579) {
            return "摩天轮";

        } else if (gameIdTemp == 1112 || gameIdTemp ==
                1152 || gameIdTemp ==
                1330 || gameIdTemp ==
                1622) {
            return "摇骰子-抽红包";

        } else if (gameIdTemp == 418 || gameIdTemp == 448 || gameIdTemp == 580 || gameIdTemp == 697 || gameIdTemp == 1339 || gameIdTemp == 1633 || gameIdTemp == 239 || gameIdTemp == 226 || gameIdTemp == 304 || gameIdTemp == 1151 || gameIdTemp == 2010) {
            return "砸金蛋";

        } else if (gameIdTemp == 1135 || gameIdTemp ==
                1138) {
            return "砸金蛋-VIP";

        } else if (gameIdTemp == 856) {
            return "砸金蛋-抽IPADKINDLE";

        } else if (gameIdTemp == 1347) {
            return "砸金蛋-抽苹果";

        } else if (gameIdTemp == 1016) {
            return "砸金蛋-视频VIP";

        } else if (gameIdTemp == 50 || gameIdTemp ==
                51 || gameIdTemp ==
                61 || gameIdTemp ==
                66 || gameIdTemp ==
                71 || gameIdTemp ==
                73 || gameIdTemp ==
                75 || gameIdTemp ==
                78 || gameIdTemp ==
                81) {
            return "主题多模式游戏";

        } else if (gameIdTemp == 1717 || gameIdTemp == 412 || gameIdTemp == 1793 || gameIdTemp == 235 || gameIdTemp == 82 || gameIdTemp == 112 || gameIdTemp == 123 || gameIdTemp == 230 || gameIdTemp == 234 || gameIdTemp == 236 || gameIdTemp == 259 || gameIdTemp == 269 || gameIdTemp == 270 || gameIdTemp == 314 || gameIdTemp == 364 || gameIdTemp == 440 || gameIdTemp == 444 || gameIdTemp == 449 || gameIdTemp == 492 || gameIdTemp == 510 || gameIdTemp == 511 || gameIdTemp == 520 || gameIdTemp == 553 || gameIdTemp == 600 || gameIdTemp == 618 || gameIdTemp == 660 || gameIdTemp == 666 || gameIdTemp == 667 || gameIdTemp == 670 || gameIdTemp == 675 || gameIdTemp == 696 || gameIdTemp == 722 || gameIdTemp == 750 || gameIdTemp == 753 || gameIdTemp == 772 || gameIdTemp == 776 || gameIdTemp == 779 || gameIdTemp == 796 || gameIdTemp == 812 || gameIdTemp == 838 || gameIdTemp == 840 || gameIdTemp == 855 || gameIdTemp == 861 || gameIdTemp == 896 || gameIdTemp == 906 || gameIdTemp == 944 || gameIdTemp == 951 || gameIdTemp == 970 || gameIdTemp == 1089 || gameIdTemp == 1092 || gameIdTemp == 1134 || gameIdTemp == 1139 || gameIdTemp == 1198 || gameIdTemp == 1212 || gameIdTemp == 1272 || gameIdTemp == 1315 || gameIdTemp == 1374 || gameIdTemp == 1401 || gameIdTemp == 1419 || gameIdTemp == 1449 || gameIdTemp == 1463 || gameIdTemp == 1601 || gameIdTemp == 1612 || gameIdTemp == 1632 || gameIdTemp == 223 || gameIdTemp == 1590 || gameIdTemp == 1721 || gameIdTemp == 2000 || gameIdTemp == 2040 || gameIdTemp == 2110 || gameIdTemp == 2162 || gameIdTemp == 2166 || gameIdTemp == 2178) {
            return "大转盘";

        } else if (gameIdTemp == 1979) {
            return "扯红包";
        } else if (gameIdTemp == 1978) {
            return "翻牌";
        } else if (gameIdTemp == 2012 || gameIdTemp == 2036) {
            return "老虎机";
        } else if (gameIdTemp == 1977) {
            return "摇签";
        } else if (gameIdTemp == 1973 || gameIdTemp == 2088) {
            return "摇骰子";
        } else if (gameIdTemp == 2190) {
            return "砸红包";
        }
        return "";
    }

}
