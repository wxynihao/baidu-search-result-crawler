package me.rainking.tool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @description:
 * @author: Rain
 * @date: 2018/3/8 16:02
 */
public class StringUtilTest {

    @Test
    public void convertForbidCharactForFileName() {
        String str = "/\\:*\"?<>|";
        Assert.assertEquals(StringUtil.convertForbidCharactForFileName(str),"         ");
    }
}