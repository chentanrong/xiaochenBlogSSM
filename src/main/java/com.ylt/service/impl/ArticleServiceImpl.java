package com.ylt.service.impl;

import com.ylt.common.Page;
import com.ylt.config.Constants;
import com.ylt.dao.AdmireDao;
import com.ylt.dao.ArticlesDao;
import com.ylt.entity.Admire;
import com.ylt.entity.Articles;
import com.ylt.service.IArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements IArticlesService {


    @Autowired
    ArticlesDao articlesDao;
    @Autowired
    AdmireDao admireDao;

    @Override
    public boolean addArticle(Articles articles) {
        articlesDao.addArticle(articles);
        return true;
    }

    @Override
    public Articles queryArticleById(String id) {
        return articlesDao.queryArticleById(id);
    }

    @Override
    public List<Articles> queryArticleByUserId(String userId) {
        List<Articles> articles = articlesDao.queryArticleByUserId(userId);
        return articles;
    }

    @Override
    public Page<Articles> queryArticleOrderByDateByPage(Integer page, Integer size) {
        Page<Articles> page1 = new Page<Articles>();
        page1.setCurrentPage(page);
        page1.setPageSize(size);
        List<Articles> list = articlesDao.queryArticleOrderByDateByPage(page1);
        page1.setItems(list);
        return page1;
    }

    @Override
    public Page<Admire> queryAdmirArticleById(String id, Integer page, Integer size) {
        Page<Admire> page1 = new Page<Admire>();
        page1.setCurrentPage(page);
        page1.setPageSize(size);
        List<Admire> list = articlesDao.getAdmireByUserByPage(page1, id, Constants.ARTICLE_TYPE);
        page1.setItems(list);
        return page1;

    }

    @Override
    public Integer addAdmire(Admire admire) {
        return admireDao.addAdmire(admire);
    }

    @Override
    public boolean deleteArticle(String articleId) {
        Integer integer = articlesDao.deleteArticle(articleId);
        return integer > 0;

    }

    @Override
    public boolean updateArticle(Articles articles) {
        Integer integer = articlesDao.updateArticle(articles);
        return integer>0;
    }

    @Override
    public Page<Articles> queryArticleByLikeByPage(String index,  Integer page, Integer size) {
        Page<Articles> page1 = new Page<Articles>();
        page1.setCurrentPage(page);
        page1.setPageSize(size);
        List<Articles> list = articlesDao.queryArticleByLikeByPage(page1,"%"+index+"%");
        page1.setItems(list);
        return page1;
    }
}
