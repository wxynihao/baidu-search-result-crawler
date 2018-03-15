package me.rainking.net;

import org.junit.Test;

/**
 * @description:
 * @author: Rain
 * @date: 2018/3/8 14:46
 */
public class BaiduSearchTest {

    BaiduSearch baiduSearch = new BaiduSearch();

    @Test
    public void getResultLinks() {
        System.out.println(baiduSearch.getResults("手机", 20).size());
    }
}