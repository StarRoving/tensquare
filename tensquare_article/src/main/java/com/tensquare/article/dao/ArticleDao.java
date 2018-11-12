package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleDao extends JpaSpecificationExecutor<Article>,JpaRepository<Article,String> {

    /**
     * 审核
     * @param id
     */
    @Modifying
    @Query("update Article set state='1' where id =?1")
    public void examine(String id);

    /**
     * 点赞
     * @param id
     * @return
     */
    @Modifying
    @Query("update Article a set thumbup = thumbup +1 where id=?1")
    public int updateThumbup(String id);
}
