package me.rainking.output;

import org.junit.Test;

/**
 * @description:
 * @author: Rain
 * @date: 2018/3/12 15:07
 */
public class ResultOutputTest {



    @Test
    public void saveSuccessKeywordToFile() {
        ResultOutput.saveSuccessKeywordToFile("./doc/tmp.txt", "sss");
    }
}