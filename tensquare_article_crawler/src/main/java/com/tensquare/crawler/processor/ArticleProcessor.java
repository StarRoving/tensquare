package com.tensquare.crawler.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 文章爬取类
 */
@Component
public class ArticleProcessor implements PageProcessor {


    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-z 0-9 -]+/article/details/[0-9]{8}").all());
        String title = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div[1]/h1/text()").get();
        String content = page.getHtml().xpath("//*[@id=\"article_content\"]/div/div[1]").get();
        System.out.println("标题"+title);
        System.out.println("内容："+content);

        if (title != null && content != null) {
            page.putField("title", title);
            page.putField("content", content);
        }else {
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3000).setSleepTime(100);
    }
}
