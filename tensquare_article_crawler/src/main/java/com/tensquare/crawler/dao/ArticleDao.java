package com.tensquare.crawler.dao;

import com.tensquare.crawler.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article> {


}
