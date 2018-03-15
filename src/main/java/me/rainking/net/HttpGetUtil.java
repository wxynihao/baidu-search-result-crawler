package me.rainking.net;


import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;


/**
 * @description:
 * @author: Rain
 * @date: 2018/3/8 11:25
 */
public class HttpGetUtil {

    final static int MAX_RETRY_TIMES = 5;

    /**
     * Http协议的get方法
     *
     * @param url 请求的链接
     * @return 服务器响应的内容部分
     */
    public static String get(String url) {

        HttpClientDownloader downloader = new HttpClientDownloader();

        if (url == null || "".equals(url)) {
            return "";
        }

        String htmlStr = null;
        System.out.println(url);

        int retryTimes = 0;


        while (htmlStr == null && retryTimes != MAX_RETRY_TIMES) {
            try {
                retryTimes++;
                Html html = downloader.download(url);
                if (html != null) {
                    htmlStr = html.get();
                }
            } catch (NullPointerException npe) {
                //最多重试后不再重试
            }
        }

        if ("".equals(htmlStr)) {
            System.out.println(url + "\t 下载失败。");
        }

        return htmlStr;
    }


}
