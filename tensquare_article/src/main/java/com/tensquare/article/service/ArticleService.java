package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 修改
     * @param article
     */
    public void update(Article article) {
        redisTemplate.delete( "article_" + article.getId() );//删除缓存
        articleDao.save(article);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        redisTemplate.delete( "article_" + id );//删除缓存
        articleDao.deleteById(id);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Article findById(String id) {
        //获取缓存
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        //如果没有缓存就存入
        if (article == null) {
            article = articleDao.findById(id).get();
            redisTemplate.opsForValue().set("article_" + id, article,1,TimeUnit.DAYS);
        }
        return article;
    }

    /**
     *文章审核
     * @param id
     */
    @Transactional
    public void examine(String id) {
        articleDao.examine(id);
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @Transactional
    public int updateThumup(String id) {
        return articleDao.updateThumbup(id);
    }
}
