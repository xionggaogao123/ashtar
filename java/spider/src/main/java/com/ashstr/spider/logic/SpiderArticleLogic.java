package com.ashstr.spider.logic;

import com.alibaba.fastjson.JSONObject;
import com.ashstr.spider.domain.SpiderArticle;
import com.ashstr.spider.enums.SpiderArticleTypeEnums;
import com.ashstr.spider.event.InsertArticleEvent;
import com.ashstr.spider.util.HttpUtil;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午4:29
 * @Description
 */
@Slf4j
@Service
public class SpiderArticleLogic {

    private static final String NEI_HAN_URL = "http://neihanshequ.com/joke";

    private static final String BU_DE_JIE_URL = "http://www.budejie.com/old-pic";

    private static final String FAN_JIAN_URL = "http://www.fanjian.net";

    private static final String PENG_FU = "https://www.pengfu.com";


    private static final String HUANGYE = "http://www.huangye88.com/search.html";

    private static final Integer BATCH_SIZE = 100;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final EventBus eventBus;

    @Autowired
    public SpiderArticleLogic( EventBus eventBus) {
        this.eventBus = eventBus;
    }


    /**
     * 爬 内涵 社区
     */
    public void spiderNeiHan(Date currentDate) {
        try {
            Map<String, String> param = Maps.newHashMap();
            param.put("is_json", "1");
            param.put("app_name", "neihanshequ_web");
            param.put("max_time", String.valueOf(currentDate));

            String response = HttpUtil.doGet(NEI_HAN_URL, param);
            JSONObject object = JSONObject.parseObject(response);
            Map<String, String> dataMap = (Map<String, String>) object.get("data");
            Object obj = dataMap.get("data");
            List<JSONObject> list = (List<JSONObject>) obj;
            List<SpiderArticle> articles = Lists.newArrayList();
            SpiderArticle article;
            for (JSONObject jsonObject : list) {
                article = new SpiderArticle();
                makeNeiHanArticle(article, jsonObject);
                articles.add(article);
            }
            eventBus.post(InsertArticleEvent.builder().spiderArticles(articles).build());
        } catch (Exception e) {
            log.error("fail spider neiHan data cause={}", Throwables.getStackTraceAsString(e));
        }
    }

    private void makeNeiHanArticle(SpiderArticle article, JSONObject jsonObject) throws Exception {
        Object group = jsonObject.get("group");
        Map<String, Object> groupMap = (Map<String, Object>) group;
        article.setArticleId((Long) groupMap.get("id"));
        article.setContent((String) groupMap.get("content"));
        article.setSharesNum((Integer) groupMap.get("share_count"));
        article.setLikesNum((Integer) groupMap.get("digg_count"));
        article.setCommentsNum((Integer) groupMap.get("comment_count"));
        Map<String, String> userData = (Map<String, String>) groupMap.get("user");
        article.setUserName(userData.get("name"));
        article.setUserAvatar(userData.get("avatar_url"));
        article.setChannel(SpiderArticleTypeEnums.NEIHAN.getValue());
    }

    /**
     * 爬 不得姐 社区
     */
    public void spiderBuDeJie() {
        List<Document> documents = getDocuments(makeBuDeJieRequestUrls());
        try {
            for (Document doc : documents) {
                if (doc == null) {
                    return;
                }
                List<SpiderArticle> articles = Lists.newArrayList();
                SpiderArticle article;
                Element dataEle = doc.getElementsByClass("j-r-c").first();
                Elements dateEles = dataEle.getElementsByClass("j-r-list");
                for (Element element : dateEles) {
                    Element ele = element.getElementsByClass("j-r-list").first();
                    Elements userEles = ele.getElementsByClass("j-list-user");
                    Elements contentEles = ele.getElementsByClass("j-r-list-c");
                    Elements toolEles = ele.getElementsByClass("j-r-list-tool");
                    Elements shareEles = ele.getElementsByClass("j-r-list-tool-ct");
                    Elements commentEles = ele.getElementsByClass("j-r-list-tool-r j-r-list-tool-cc");
                    Elements likesEles = toolEles.select("div.j-r-list-tool-l ul");
                    Elements idEles = toolEles.select("div.j-r-list-tool-l");

                    for (int i = 0; i < userEles.size(); i++) {
                        article = new SpiderArticle();
                        makeBuDeJieArticle(article, idEles, i, userEles, contentEles, likesEles, shareEles, commentEles);
                        articles.add(article);
                    }
                }
                eventBus.post(InsertArticleEvent.builder().spiderArticles(articles).build());
            }
        } catch (Exception e) {
            log.error("fail parse document with cause={}", Throwables.getStackTraceAsString(e));
        }
    }

    private List<String> makeBuDeJieRequestUrls() {
        List<String> urls = Lists.newArrayList();
        for (int i = 1; i <= BATCH_SIZE; i++) {
            String urlStr = BU_DE_JIE_URL + "/" + i;
            urls.add(urlStr);
        }
        return urls;
    }

    private void makeBuDeJieArticle(SpiderArticle article, Elements idEles, int i, Elements userEles, Elements contentEles,
                                    Elements likesEles, Elements shareEles, Elements commentEles) throws Exception {
        //id
        article.setArticleId(Long.valueOf(idEles.get(i).attributes().get("data-id")));
        Element userEle = userEles.get(i);
        //用户名称
        article.setUserName(userEle.select("div.u-txt a").first().text());
        //用户头像
        Element userAvatar = userEle.select("div.u-img a img").first();
        if (userAvatar != null) {
            Map<String, String> dataSetMap = userAvatar.dataset();
            if (!dataSetMap.isEmpty()) {
                article.setUserAvatar(dataSetMap.get("original"));
            }
        }
        Element contentEle = contentEles.get(i);
        article.setTitle(contentEle.select("div.j-r-list-c-desc a").first().text());
        //图片
        Element picEle = contentEle.select("div.j-r-list-c-img a img").first();
        Map<String, String> dataSetPic = picEle.dataset();
        article.setImageUrl(dataSetPic.get("original"));

        Element e = likesEles.get(i);
        String likes = e.select("ul span").first().text();
        article.setLikesNum(Integer.valueOf(likes));
        //分享次数；返回格式为 分享 9991
        String shareNums = shareEles.select("div.j-r-list-tool-ct-share-c span").first().text();
        //评论数
        Elements shareNumEles = commentEles.select("div.j-r-list-tool-r j-r-list-tool-cc li");
        for (int n = 0; n < shareNumEles.size(); n++) {
            Element shareEle = shareNumEles.get(n);
            if (n == 1) {
                String comments = shareEle.getElementsByClass("li span").first().text();
                article.setCommentsNum(Integer.valueOf(comments));
            }
        }
        //渠道
        article.setChannel(SpiderArticleTypeEnums.BUDEJIE.getValue());
    }

    /**
     * 爬 犯贱 社区
     */
    public void spiderFanJian() {
        List<Document> documents = getDocuments(makeFanJianRequestUrl());
        try {
            for (Document doc : documents) {
                if (doc == null) {
                    return;
                }
                List<SpiderArticle> articles = Lists.newArrayList();
                SpiderArticle article;

                Element baseElement = doc.getElementsByAttributeValue("class", "b").first();
                Elements elements = baseElement.getElementsByTag("li");
                for (Element element : elements) {
                    article = new SpiderArticle();
                    makeFanJianArticle(element, article);
                    articles.add(article);
                }
                eventBus.post(InsertArticleEvent.builder().spiderArticles(articles).build());
            }
        } catch (Exception e) {
            log.error("fail parse document cause={}", Throwables.getStackTraceAsString(e));
        }
    }

    private List<String> makeFanJianRequestUrl() {
        List<String> urls = Lists.newArrayList();
        for (int i = 1; i <= BATCH_SIZE; i++) {
            String urlStr = FAN_JIAN_URL + "/duanzi-" + i;
            urls.add(urlStr);
        }
        return urls;
    }

    private void makeFanJianArticle(Element element, SpiderArticle article) {
        Element idEle = element.select("div.cont-list-reward a").first();
        Map<String, String> stringMap = idEle.dataset();
        //id
        article.setArticleId(Long.valueOf(stringMap.get("id")));

        //用户名称
        article.setUserName(element.select("div.joke-list-name a").first().text());

        //用户头像
        Element userAvatar = element.select("div.cont-list-reward a img").first();
        article.setUserAvatar(userAvatar.dataset().get("src"));

        //获取内容
        article.setContent(element.select("div.joke-list-txt").first().text());

        //other
        Elements otherEles = element.select("div.cont-list-tools a");
        for (int i = 0; i < otherEles.size(); i++) {
            Element e = otherEles.get(i);
            if (i == 0) {
                //点赞数
                String likes = e.select("a i").first().text();
                article.setLikesNum(Integer.valueOf(likes));
            }
            if (i == 2) {
                //评论数
                String commentsNum = e.select("a i").first().text();
                article.setCommentsNum(Integer.valueOf(commentsNum));
            }
        }
        //渠道
        article.setChannel(SpiderArticleTypeEnums.FANJIAN.getValue());
    }

    /**
     * 爬 捧腹 社区
     */
    public void spiderPengFu() {
        List<Document> documents = getDocuments(makePengFuRequestUrl());
        try {
            for (Document doc : documents) {
                List<SpiderArticle> articles = Lists.newArrayList();
                SpiderArticle article;
                Element ele = doc.getElementsByClass("w645 fl").first();
                Elements elements = ele.getElementsByClass("list-item bg1 b1 boxshadow");

                for (Element element : elements) {
                    article = new SpiderArticle();
                    makePengFuArticle(article, element);
                    articles.add(article);
                }
                eventBus.post(InsertArticleEvent.builder().spiderArticles(articles).build());
            }
        } catch (Exception e) {
            log.error("fail parse document cause={}", Throwables.getStackTraceAsString(e));
        }
    }

    private List<String> makePengFuRequestUrl() {
        List<String> urls = Lists.newArrayList();
        for (int i = 1; i <= BATCH_SIZE; i++) {
            String urlStr = PENG_FU + "/index_" + i + ".html";
            urls.add(urlStr);
        }
        return urls;
    }

    private void makePengFuArticle(SpiderArticle article, Element element) throws Exception {
        //id
        article.setArticleId(Long.valueOf(element.attributes().get("id")));

        //用户名
        article.setUserName(element.select("p.user_name_list a").first().text());

        // 用户头像
        Element userAvatarEle = element.select("dt a img").first();
        article.setUserAvatar(userAvatarEle.attributes().get("src"));

        //获取标题
        article.setTitle(element.select("h1.dp-b a").first().text());

        //长图片,可能也没有
        Element picEle = element.select("div.content-img img").first();
        if (picEle != null) {
            Attributes attributes = picEle.attributes();
            //有二种格式，jgpsrc , gifsrc
            String pic = attributes.get("jpgsrc");
            if (Strings.isNullOrEmpty(pic)) {
                pic = attributes.get("gifsrc");
            }
            article.setImageUrl(pic);
        }
        //内容，也可能没有
        Element contentEle = element.select("div.content-img").first();
        if (contentEle != null) {
            String content = contentEle.text();
            article.setContent(content);
        }
        //other
        Elements otherEle = element.select("div.fl span");
        for (int i = 0; i < otherEle.size(); i++) {
            Element e = otherEle.get(i);
            if (i == 0) {
                //点赞数
                article.setLikesNum(Integer.valueOf(e.select("span").first().text()));
            }
            if (i == 2) {
                //评论数
                article.setCommentsNum(Integer.valueOf(e.select("span").first().text()));
            }
        }
        article.setChannel(SpiderArticleTypeEnums.PENGFU.getValue());
    }

    private List<Document> getDocuments(List<String> urls) {
        //获取documents 集合
        List<Document> documents = Lists.newArrayList();
        Document document;
        try {
            for (String url : urls) {
                document = Jsoup.connect(url).get();
                documents.add(document);
            }
        } catch (Exception e) {
            log.error("fail load html cause={}", Throwables.getStackTraceAsString(e));
        }
        return documents;
    }

}
