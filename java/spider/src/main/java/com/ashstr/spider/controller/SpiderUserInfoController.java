package com.ashstr.spider.controller;


import com.ashstr.common.util.ExcelUtil;
import com.ashstr.common.util.HttpFileDownloadUtil;
import com.ashstr.spider.domain.SpiderUserInfo;
import com.ashstr.spider.service.SpiderUserInfoService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author keven
 * @date 2018-06-08 下午4:27
 * @Description
 */
@RestController
public class SpiderUserInfoController {

    @Resource
    private SpiderUserInfoService spiderUserInfoService;

    private static final String HUANGYE = "http://www.huangye88.com/search.html";

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("download")
    public void download(HttpServletResponse response) {
        List<SpiderUserInfo> spiderUserInfos = spiderUserInfoService.listAll();
        String sheetName = "数据";
        HSSFWorkbook workbook = ExcelUtil.createExcel(null, spiderUserInfos, getDate(), sheetName );
        HttpFileDownloadUtil.downloadExcel(response, workbook, sheetName);
    }

    private LinkedHashMap<String, String> getDate() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("用户姓名", "name");
        map.put("电话号码", "telPhone");
        map.put("地址", "address");
        map.put("公司名称", "companyName");
        map.put("主营产品", "mainProduct");
        map.put("关键字", "keyword");
        return map;
    }

    @GetMapping("spider-baojian")
    public void spiderBaoJian() {
        String keyWord = "保健品";
        String type = "company";
        List<String> urls = Lists.newArrayList();

        for (int i =2; i< 500; i++) {
            String url = HUANGYE + "?kw=" + keyWord + "&type=" + type + "&page="+i +"/";
            urls.add(url);
        }

        urls.forEach(this::spiderCompanyInfo);
    }


    @GetMapping("spider-lingsi")
    public void spiderLingSi() {
        String keyWord = "零食";
        String type = "company";
        List<String> urls = Lists.newArrayList();

        for (int i =2; i< 500; i++) {
            String url = HUANGYE + "?kw=" + keyWord + "&type=" + type + "&page="+i +"/";
            urls.add(url);
        }

        urls.forEach(this::spiderCompanyInfo);
    }


    @GetMapping("spider-riyong")
    public void spiderRiYong() {
        String keyWord = "日用品";
        String type = "company";
        List<String> urls = Lists.newArrayList();

        for (int i =2; i< 500; i++) {
            String url = HUANGYE + "?kw=" + keyWord + "&type=" + type + "&page="+i +"/";
            urls.add(url);
        }

        urls.forEach(this::spiderCompanyInfo);
    }


    @GetMapping("spider-huazhuang")
    public void spiderHuazhuang() {
        String keyWord = "化妆品";
        String type = "company";
        List<String> urls = Lists.newArrayList();

        for (int i =2; i< 500; i++) {
            String url = HUANGYE + "?kw=" + keyWord + "&type=" + type + "&page="+i +"/";
            urls.add(url);
        }

        urls.forEach(this::spiderCompanyInfo);
    }


    @GetMapping("spider-muying")
    public void spiderMuYing() {
        String keyWord = "母婴";
        String type = "company";
        List<String> urls = Lists.newArrayList();

        for (int i =2; i< 40; i++) {
            String url = HUANGYE + "?kw=" + keyWord + "&type=" + type + "&page="+i +"/";
            urls.add(url);
        }

        urls.forEach(this::spiderCompanyInfo);
    }




    @GetMapping("spider-company")
    public void makeUrl() {
        String keyWord = "房地产";
        String type = "company";
        List<String> urls = Lists.newArrayList();

        for (int i =4; i< 420; i++) {
            String url = HUANGYE + "?kw=" + keyWord + "&type=" + type + "&page="+i +"/";
            urls.add(url);
        }

        urls.forEach(this::spiderCompanyInfo);
    }

    private   void spiderCompanyInfo(String url) {
        Map<String, String> map = Maps.newHashMap();
        map.put("Content-type", "application/x-www-form-urlencoded");
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Upgrade-Insecure-Requests", "1");
        map.put("Origin", "http://www.huangye88.com");
        map.put("Referer", "http://www.huangye88.com/search.html?kw=%E6%88%BF%E5%9C%B0%E4%BA%A7&type=company");
        map.put("Host", "www.huangye88.com");

        try {
            Document document1 = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36)")
                    .headers(map).get();

            Element element = document1.getElementsByClass("big-pro").first();
            List<Node> nodeList = element.childNodes();
            nodeList = nodeList.stream().filter(node -> !(node.childNodes().size() == 0)).collect(Collectors.toList());
            markUserInfo(nodeList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/spider")
    public void spiderHuangYe() throws Exception {

        String keyWord = "房地产";
        String type = "company";

        String url = HUANGYE + "?kw=" + keyWord + "&type=" + type;

        Map<String, String> param = Maps.newHashMap();
        param.put("a", String.valueOf(0));
        param.put("kw", "房地产");
        param.put("type", "company");
        param.put("c", String.valueOf(0));
        param.put("uu", String.valueOf(1));
        //param.put("cattrace", null);
        param.put("pagetype", String.valueOf(13));

        Map<String, String> map = Maps.newHashMap();
        map.put("Content-type", "application/x-www-form-urlencoded");
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Upgrade-Insecure-Requests", "1");
        map.put("Origin", "http://www.huangye88.com");
        map.put("Referer", "http://www.huangye88.com/search.html?kw=%E6%88%BF%E5%9C%B0%E4%BA%A7&type=company");
        map.put("Host", "www.huangye88.com");
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36)")
                .headers(map).data(param).post();

        Element element = document.getElementsByClass("big-pro").first();
        List<Node> nodeList = element.childNodes();
        nodeList = nodeList.stream().filter(node -> !(node.childNodes().size() == 0)).collect(Collectors.toList());
        markUserInfo(nodeList);
    }


    private void markUserInfo(List<Node> nodes) {
        SpiderUserInfo userInfo;
        List<SpiderUserInfo> userInfos = Lists.newArrayList();
        for (Node node : nodes) {
            userInfo = new SpiderUserInfo();
            String str = node.toString();
            Document document = Jsoup.parse(str);
            String title = document.getElementsByClass("p-title").text();
            Elements elements = document.getElementsByClass("com");
            String name = document.getElementsByClass("fen").next().text();
            if (!Strings.isNullOrEmpty(name)) {
                String[] a = name.split(" ");
                name = a[0];
            }
            if (elements.size() < 3) {
                continue;
            }
            String mainProduct = elements.get(0).text();
            String address = elements.get(1).text().substring(3);
            String phone = elements.get(2).text().substring(4);

            userInfo.setCompanyName(title);
            userInfo.setAddress(address);
            userInfo.setMainProduct(mainProduct);
            userInfo.setTelPhone(phone);
            userInfo.setName(name);
            //1 表示 黄页88
            userInfo.setChannel(4);
            userInfo.setKeyword("保健品");
            userInfos.add(userInfo);
        }

        createUserInfo(userInfos);
    }




    public void createUserInfo(List<SpiderUserInfo> infos) {
        try {
            for (SpiderUserInfo userInfo : infos) {
                spiderUserInfoService.createUserInfo(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
