package me.rainking;

import me.rainking.input.KeyWordsInput;
import me.rainking.input.PropertiesSettings;
import me.rainking.output.FileOutput;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 主程序
 * @author: Rain
 * @date: 2018/3/8 14:42
 */
public class Application {

    public static void main(String[] args) throws IOException {

        //共5个参数需要配置
        PropertiesSettings prop = PropertiesSettings.getProperties();
        //1.存储关键词的文件的路径，在该文件所在文件夹会创建success.txt用于存储下载成功的关键词组
        String keywordsFilePath = prop.getKeywordsFilePath();
        //2.存储下载的搜索结果文件的目录的路径，每行关键词组会创建一个文件
        String outputDirPath = prop.getOutputDirPath();
        //3.每个关键词需要的结果数量
        int resultSize = prop.getResultSize();
        //4.之前设置了期望的resultSize，实际下载数会少于resultSize，当实际值达到thresholdOfSuccess时认为下载成功
        int thresholdOfSuccess = prop.getThresholdOfSuccess();
        //5.线程数量
        int threadSize = prop.getThreadSize();


        KeyWordsInput keyWordsInput = new KeyWordsInput();

        File keywordsFile = new File(keywordsFilePath);

        String successFilePath = keywordsFile.getParent() + "\\success.txt";

        List<String> keywords = keyWordsInput.getKeywordFromFile(keywordsFilePath, successFilePath);

        List<List<String>> keyWordsListList = averageAssign(keywords, threadSize);

        int threadId = 0;
        for (List<String> keyWordsList : keyWordsListList) {
            new Thread(new FileOutput(keyWordsList, resultSize, outputDirPath, successFilePath, threadId++, thresholdOfSuccess)).start();
        }
    }

    /**
     * 将一个list均分成n个list
     *
     * @param source
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;
        int number = source.size() / n;
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
