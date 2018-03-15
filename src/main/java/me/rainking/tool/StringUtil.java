package me.rainking.tool;

/**
 * @description:
 * @author: Rain
 * @date: 2018/3/8 15:58
 */
public class StringUtil {

    /**
     * 替换掉文件名中不允许使用的字符
     *
     * @param fileName
     * @return
     */
    public static String convertForbidCharactForFileName(String fileName) {
        return fileName
                .replaceAll("/", " ")
                .replaceAll("\\\\", " ")
                .replaceAll(":", " ")
                .replaceAll("\\*", " ")
                .replaceAll("\\?", " ")
                .replaceAll("\"", " ")
                .replaceAll("<", " ")
                .replaceAll(">", " ")
                .replaceAll("\\|", " ")
                .replaceAll("\t", " ")
                .trim()
                ;
    }

    /**
     * 删除全部会导致换行的空白字符
     *
     * @param str
     * @return
     */
    public static String removeLineTag(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("\\n", " ").replaceAll("\\r", " ");
    }
}
