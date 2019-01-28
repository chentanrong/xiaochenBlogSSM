package com.ylt.service;

import com.ylt.common.Page;
import com.ylt.entity.Admire;
import com.ylt.entity.Articles;

import java.util.List;

public interface IArticlesService {

    public boolean addArticle(Articles articles);

    public boolean deleteArticle(String articleId);
    public boolean updateArticle(Articles articles);

    public Articles queryArticleById(String id);

    public List<Articles> queryArticleByUserId(String userId);

    public Page<Articles> queryArticleOrderByDateByPage(Integer page, Integer size);

    Page<Admire> queryAdmirArticleById(String id,Integer page,Integer size);

    public Integer addAdmire(Admire admire);

    public Page<Articles> queryArticleByLikeByPage(String index,Integer page,Integer size);

}
