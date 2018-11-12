package com.tensquare.crawler.task;

import com.tensquare.crawler.pipeline.ArticleDbPipeline;
import com.tensquare.crawler.pipeline.ArticleTxtPipeline;
import com.tensquare.crawler.processor.ArticleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 文章任务类
 */
@Component
public class ArticleTask {

    @Autowired
    private ArticleDbPipeline articleDbPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    @Autowired
    private ArticleProcessor articleProcessor;

//    @Autowired
//    private ArticleTxtPipeline articleTxtPipeline;

    /**
     * ai
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void aiTask() {
        System.out.println("爬取AI文章");
        Spider spider = Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/ai");
        articleDbPipeline.setChannelId("ai");

        spider.addPipeline(articleDbPipeline);
//        spider.addPipeline(articleTxtPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }
}
