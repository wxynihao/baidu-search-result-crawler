# 1 概述

该项目是一个百度搜索结果获取爬虫。

爬虫从一个文本文件中获取关键字组，查询获得结果后，将结果链接的内容下载存储到文件中。

# 2 使用

1. 下载jar文件和配置文件。 [百度网盘]()
2. 修改配置文件中的配置，最主要的是要指定关键字文件。
3. 运行run.bat，即可开始爬取。
4. 如果需要删除输出文件夹中的文件，需一并删除success.txt文件。

# 3 运行流程
1. 读取配置文件settings.properties
2. 根据配置中指定的关键字文件路径，读取关键字
3. 根据已下载文件success.txt，过滤掉已下载的关键字
4. 发百度发送关键字查询
5. 获取和解析百度的查询结果
6. 请求查询结果指向的链接
7. 获取链接指向的页面中的文本内容
8. 将内容写入文件
9. 如果获取的结果数量达到配置的最小值，则将关键字写入已下载文件success.txt。

# 4 关键问题
## 4.1 页面乱码问题

直接使用httpclient获取页面会频繁出现乱码问题，即使添加了从页面获取charset的逻辑也不能很好的解决这个问题。

之前使用webmagic感觉很少出现乱码问题，所有我就直接使用了webmagic的下载器。

``` java
HttpClientDownloader downloader = new HttpClientDownloader();
Html html = downloader.download(url);
String htmlStr = html.get();
```

## 4.2 运行时乱码问题
运行时乱码问题与使用的系统有关，此时运行时需要指定编码格式。
``` bat
java -jar -Dfile.encoding=utf-8 filename.jar
```

## 4.3 百度的查询

百度的查询链接形如: https://www.baidu.com/s?wd=%E5%85%B3%E9%94%AE%E5%AD%97&pn=0 

其中wd表示的是关键字，需要使用URLEncoder进行处理，pn表示的是页号，pn = (page-1)*10 ，默认页面大小是10。

## 4.4 百度查询结果的解析

百度的查询结果包括广告链接、指向百度自己产品的链接、其他结果链接，其中广告链接不计入页面大小的计数中。

所有链接的标题均使用h3标签，指向百度自己产品的链接的class为t c-gap-bottom-small，而指向其他结果链接的为t。

使用jsoup根据上面的规则可以很容易获取到结果链接。

