package me.rainking.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @description:
 * @author: Rain
 * @date: 2018/3/12 14:35
 */
public class ResultOutput {

    /**
     * 将下载成功的关键词组写入文件（需要线程安全）
     *
     * @param successFilePath
     * @param keyword
     */
    public synchronized static void saveSuccessKeywordToFile(String successFilePath, String keyword) {

        //文件不存在时创建文件，并将关键词写入
        File successFile = new File(successFilePath);
        if (!successFile.exists()) {
            try {
                successFile.getParentFile().mkdirs();
                successFile.createNewFile();
            } catch (IOException e) {
                System.out.println("存储下载成功关键词的文件创建失败！");
                return;
            }
        }

        //文件存在时，写入文件
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(successFilePath, true);
        } catch (IOException e) {
            System.out.println("存储下载成功关键词的文件打开失败！");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            fileWriter.append(keyword + "\n");
        } catch (IOException e) {
            System.out.println("存储下载成功关键词的文件写入失败！");
        }

        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("存储下载成功关键词的文件关闭失败！");
        }

    }

}
