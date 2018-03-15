package me.rainking.input;

import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * @author: Rain
 * @date: 2018/3/8 11:12
 */
public class KeyWordsInputTest {

    KeyWordsInput keyWordsInput = new KeyWordsInput();

    @Test
    public void getKeywordFromeFile() throws IOException {
        System.out.println(keyWordsInput.getKeywordFromFile("./doc/file_relation_withExample.txt","./doc/success.txt").size());
    }
}