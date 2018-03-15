package me.rainking.net;

import me.rainking.input.PropertiesSettings;
import me.rainking.tool.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @description: 从百度获取搜索结果
 * @author: Rain
 * @date: 2018/3/8 11:36
 */
public class BaiduSearch {

    /**
     * 每页包含的结果数
     */
    final static int SIZE_OF_PAGE_RESULT = 10;

    /**
     * 下载时最大重试次数
     */
    final static int MAX_RETRY_TIMES = 5;

    /**
     * 获取某关键字指定数量结果内容
     *
     * @param keywords   关键字组
     * @param resultSize 结果数量，不满整十的按整十计算，如55按60计算。
     * @return 结果内容链表
     */
    public List<String> getResults(String keywords, int resultSize) {
        return getResultLinks(keywords, resultSize)
                .stream()
                .map(link -> HttpGetUtil.get(link))
                .filter(html -> html != null)
                .map(html -> convertHtml2Text(html))
                .map(pageContent -> StringUtil.removeLineTag(pageContent))
                .collect(toList())
                ;
    }

    public String convertHtml2Text(String html) {
        Document document = Jsoup.parse(html);
        String text = document.body().text()
                // .replaceAll("\\s+"," ")
                //.trim()
                ;

        text = Arrays.asList(text.split("\\s+")).stream()
                .filter(str -> str.length() > PropertiesSettings.getProperties().getMinLengthOfValidStr())
                .collect(Collectors.joining(PropertiesSettings.getProperties().getDelimiterOfValidStr()))
        ;
        return text;
    }

    /**
     * 获取某关键字指定数量结果链接
     *
     * @param keywords   关键字组
     * @param resultSize 结果数量，不满整十的按整十计算，如55按60计算。
     * @return 结果链接列表
     */
    private List<String> getResultLinks(String keywords, int resultSize) {

        List<String> resultLinks = new ArrayList<>();

        int pageCount;
        if (resultSize % SIZE_OF_PAGE_RESULT == 0) {
            pageCount = resultSize / SIZE_OF_PAGE_RESULT;
        } else {
            pageCount = resultSize / SIZE_OF_PAGE_RESULT + 1;
        }

        for (int page = 1; page < pageCount + 1; page++) {
            String pageHtml = getResultListPage(keywords, page);
            resultLinks.addAll(getResultsFromHtml(pageHtml));
        }

        return resultLinks;
    }


    /**
     * 获取某关键字指定页的链接
     *
     * @param keywords 关键字
     * @param page     页码
     * @return 结果页链接
     */
    private String getResultListPage(String keywords, int page) {

        page = (page - 1) * 10;

        String targetUrl = "";

        try {
            targetUrl = "https://www.baidu.com/s?wd=" + URLEncoder.encode(keywords, "utf8") + "&pn=" + page;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return targetUrl;
    }

    /**
     * 从结果页面html中获取结果链接
     *
     * @param pageUrl 结果页面Url
     * @return 页面中的结果链接
     */
    private List<String> getResultsFromHtml(String pageUrl) {

        List<String> pageResultLinks = new ArrayList<>();

        Document doc = null;

        int retryTimes = 0;

        //使用jsoup的下载方法，HttpGetUtil中的get下载的页面不能解析，原因不明
        while (doc == null && retryTimes != MAX_RETRY_TIMES) {
            try {
                retryTimes++;
                doc = Jsoup.connect(pageUrl).get();
            } catch (IOException e) {
                doc = null;
            } catch (UncheckedIOException e) {
                doc = null;
            }
        }

        if (doc == null) {
            System.out.println("哎呀呀，" + pageUrl + "下了" + MAX_RETRY_TIMES + "遍都失败了");
            return pageResultLinks;
        }

        Elements h3Title = doc.select("h3");

        for (Element element : h3Title) {
            String className = element.className();
            if ("t".equals(className) || "t c-gap-bottom-small".equals(className)) {
                String href = element.getElementsByTag("a").first().attr("href");
                pageResultLinks.add(href);
            }
        }
        return pageResultLinks;
    }

}
