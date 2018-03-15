package me.rainking.output;

import me.rainking.net.BaiduSearch;
import me.rainking.tool.StringUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @description: 将搜索结果写入文件
 * @author: Rain
 * @date: 2018/3/8 14:42
 */
public class FileOutput implements Runnable {

    /**
     * 由该线程负责完成的关键字列表
     */
    List<String> keywordList;

    int resultSize;

    String saveDirectory;

    int threadId;

    String successFilePath;

    int thresholdOfSuccess;

   public FileOutput(List<String> keywordList, int resultSize, String saveDirectory, String successFilePath, int threadId, int thresholdOfSuccess) {
        this.keywordList = keywordList;
        this.resultSize = resultSize;
        this.saveDirectory = saveDirectory;
        this.successFilePath = successFilePath;
        this.threadId = threadId;
        this.thresholdOfSuccess = thresholdOfSuccess;
    }

    public List<String> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<String> keywordList) {
        this.keywordList = keywordList;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    public String getSaveDirectory() {
        return saveDirectory;
    }

    public void setSaveDirectory(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    BaiduSearch baiduSearch = new BaiduSearch();

    /**
     * 将关键词组和获取到的页面html写入文件
     *
     * @param keyWords 关键词组
     * @param resultSize 期望的结果数量
     * @param saveDirectory 存储路径
     * @return 是否下载成功
     * @throws IOException
     */
    public boolean saveResultsToFile(String keyWords, int resultSize, String saveDirectory) throws IOException {

        File directory = new File(saveDirectory);
        directory.mkdirs();

        boolean isSuccess = false;

        List<String> results = baiduSearch.getResults(keyWords, resultSize);

        String keyWordsAsFileName = StringUtil.convertForbidCharactForFileName(keyWords);
        String filePath = saveDirectory + "/" + keyWordsAsFileName + ".txt";

        File file = new File(filePath);
        file.createNewFile();

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(keyWordsAsFileName);

        for (String result : results) {
            bufferedWriter.write("\n" + result);
        }

        bufferedWriter.close();
        fileWriter.close();

        if (file.exists() && results.size() >= thresholdOfSuccess) {
            isSuccess = true;
        }

        if (isSuccess) {
            ResultOutput.saveSuccessKeywordToFile(successFilePath, keyWords);
        }

        return isSuccess;
    }

    @Override
    public void run() {

        int countOfDown = 1;

        for (String str : keywordList) {
            try {
                saveResultsToFile(str, resultSize, saveDirectory);
                System.out.println("Thread" + threadId + ":\t" + (countOfDown++) + "/" + keywordList.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
