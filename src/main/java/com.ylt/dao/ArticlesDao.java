package com.ylt.dao;

import com.ylt.common.Page;
import com.ylt.entity.Admire;
import com.ylt.entity.Articles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticlesDao {

    public boolean addArticle(Articles articles);

    public Integer updateArticle(Articles articles);

    public Integer deleteArticle(String articleId);

    public Articles queryArticleById(String id);

    public List<Articles> queryArticleByUserId(String userId);

    public List<Articles> queryArticleOrderByDateByPage(Page page);


    public List<Articles> queryArticleByLikeByPage(@Param("page")Page page, @Param("index")String index);

    public List<Admire> getAdmireByUserByPage(@Param("page")Page page, @Param("userId")String userId, @Param("type")String type);
}
