package me.rainking.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 从文件中获取搜索关键词
 * @author: Rain
 * @date: 2018/3/8 9:07
 */
public class KeyWordsInput {

    /**
     * 根据关键词文件和已成功文件生成待下载列表
     *
     * @param keywordsFilePath 关键词文件
     * @param successFilePath  已成功文件
     * @return 待下载关键词组列表
     * @throws IOException
     */
    public List<String> getKeywordFromFile(String keywordsFilePath, String successFilePath) throws IOException {

        List<String> successList = new ArrayList<>();
        List<String> keywordList = new ArrayList<>();

        String line = "";
        if ((new File(successFilePath).exists())) {
            FileReader fileReader = new FileReader(successFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                successList.add(line.trim());
            }
            bufferedReader.close();
            fileReader.close();
        }

        System.out.println("已下载关键词组" + successList.size() + "条。");

        if ((new File(keywordsFilePath).exists())) {
            FileReader fileReader = new FileReader(keywordsFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String keywords = getKeywordFromLine(line);
                if (!successList.contains(keywords)) {
                    keywordList.add(keywords.trim());
                }
            }
            bufferedReader.close();
            fileReader.close();
        }

        System.out.println("待下载关键词组" + keywordList.size() + "条。");

        return keywordList;
    }

    /**
     * 关键词预处理
     * 将空白字符分隔的关键字加上双引号，并用一个空格隔开
     *
     * @param lineText 一行包含关键字的文本，如：关键字一  关键字二\t关键字三）
     * @return 添加关键字组（如："关键字一" "关键字二" "关键字三"）
     */
    private String getKeywordFromLine(String lineText) {

        String keyWords = Arrays.stream(lineText.trim().split("\\s+"))
                .collect(Collectors.joining("\" \"", "\"", "\""));

        return keyWords;
    }

}
