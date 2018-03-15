package me.rainking.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Rain
 * @description
 * @date 2018/3/13 9:13
 */

public class PropertiesSettings {

    /**
     * 1.存储关键词的文件的路径，在该文件所在文件夹会创建success.txt用于存储下载成功的关键词组
     */
    private String keywordsFilePath = "./doc/keywords.txt";
    /**
     * 2.存储下载的搜索结果文件的目录的路径，每行关键词组会创建一个文件
     */
    private String outputDirPath = "./doc/out";
    /**
     * 3.每个关键词需要的结果数量
     */
    private int resultSize = 20;
    /**
     * 4.之前设置了期望的resultSize，实际下载数会少于resultSize，当实际值达到thresholdOfSuccess时认为下载成功
     */
    private int thresholdOfSuccess = 10;
    /**
     * 5.线程数量
     */
    private int threadSize = 10;

    /**
     * 6.有效文本的最短长度
     */
    private int minLengthOfValidStr = 0;

    /**
     * 7.有效文本之间的分隔符
     */
    private String delimiterOfValidStr = "***";

    private static PropertiesSettings propertiesSettings = new PropertiesSettings();

    private static boolean hasLoad = false;

    public static PropertiesSettings getProperties() {

        if (hasLoad) {
            return propertiesSettings;
        }

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("settings.properties")));

            String keywordsFilePathSet = prop.getProperty("keywordsFilePath");
            if (keywordsFilePathSet != null) {
                propertiesSettings.setKeywordsFilePath(keywordsFilePathSet);
            }
            String outputDirPathSet = prop.getProperty("outputDirPath");
            if (outputDirPathSet != null) {
                propertiesSettings.setOutputDirPath(outputDirPathSet);
            }
            String resultSizeSet = prop.getProperty("resultSize");
            if (resultSizeSet != null) {
                propertiesSettings.setResultSize(Integer.parseInt(resultSizeSet));
            }
            String thresholdOfSuccessSet = prop.getProperty("thresholdOfSuccess");
            if (thresholdOfSuccessSet != null) {
                propertiesSettings.setThresholdOfSuccess(Integer.parseInt(thresholdOfSuccessSet));
            }
            String threadSizeSet = prop.getProperty("threadSize");
            if (threadSizeSet != null) {
                propertiesSettings.setThreadSize(Integer.parseInt(threadSizeSet));
            }

            String minLengthOfValidStrSet = prop.getProperty("minLengthOfValidStr");
            if (minLengthOfValidStrSet != null) {
                propertiesSettings.setMinLengthOfValidStr(Integer.parseInt(minLengthOfValidStrSet));
            }

            String delimiterOfValidStr = prop.getProperty("delimiterOfValidStr");
            if (delimiterOfValidStr != null) {
                propertiesSettings.setDelimiterOfValidStr(delimiterOfValidStr);
            }

            hasLoad = true;


        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("没有找到配置文件，加载默认设置。");
        } catch (IOException e) {

        }
        return propertiesSettings;
    }


    public String getKeywordsFilePath() {
        return keywordsFilePath;
    }

    public void setKeywordsFilePath(String keywordsFilePath) {
        this.keywordsFilePath = keywordsFilePath;
    }

    public String getOutputDirPath() {
        return outputDirPath;
    }

    public void setOutputDirPath(String outputDirPath) {
        this.outputDirPath = outputDirPath;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    public int getThresholdOfSuccess() {
        return thresholdOfSuccess;
    }

    public void setThresholdOfSuccess(int thresholdOfSuccess) {
        this.thresholdOfSuccess = thresholdOfSuccess;
    }

    public int getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

    public int getMinLengthOfValidStr() {
        return minLengthOfValidStr;
    }

    public void setMinLengthOfValidStr(int minLengthOfValidStr) {
        this.minLengthOfValidStr = minLengthOfValidStr;
    }

    public String getDelimiterOfValidStr() {
        return delimiterOfValidStr;
    }

    public void setDelimiterOfValidStr(String delimiterOfValidStr) {
        this.delimiterOfValidStr = delimiterOfValidStr;
    }

    @Override
    public String toString() {
        return "PropertiesSettings{" +
                "keywordsFilePath='" + keywordsFilePath + '\'' +
                ", outputDirPath='" + outputDirPath + '\'' +
                ", resultSize=" + resultSize +
                ", thresholdOfSuccess=" + thresholdOfSuccess +
                ", threadSize=" + threadSize +
                ", minLengthOfValidStr=" + minLengthOfValidStr +
                ", delimiterOfValidStr='" + delimiterOfValidStr + '\'' +
                '}';
    }
}
