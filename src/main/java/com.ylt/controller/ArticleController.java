package com.ylt.controller;

import com.ylt.common.JsonResult;
import com.ylt.common.Page;
import com.ylt.config.Constants;
import com.ylt.dao.AdmireDao;
import com.ylt.entity.Admire;
import com.ylt.entity.Articles;
import com.ylt.service.IArticlesService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Api(tags = "文章模块")
@Controller
public class ArticleController {


    @Resource
    IArticlesService articlesService;
    @Autowired
    AdmireDao admireDao;

    @RequestMapping(value = "/addArticle.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addArticle(@RequestBody(required = true) Map<String, Object> map, HttpServletRequest request) {
        String title = map.get("title").toString();
        String content = map.get("content").toString();
        String views = map.get("views").toString();
        String userId = (String) request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }

        Articles article = new Articles();
        article.setUserId(userId);
        article.setArticleTitle(title);
        article.setArticleContent(content);
        article.setArticleViews(views);
        article.setArticleDate(DateFormatUtils.format(new Date(), Constants.DATE_PATTERN));
        article.setArticleId(UUID.randomUUID().toString());
        boolean addArticle = articlesService.addArticle(article);
        return JsonResult.success("操作成功");
    }


    @RequestMapping(value = "/getArticleByUser", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getArticleByUser(@RequestParam("userId") String userId) {
        List<Articles> articles = articlesService.queryArticleByUserId(userId);
        return JsonResult.success(articles);
    }

    @RequestMapping(value = "/queryArticleById", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryArticleById(@RequestParam("id") String id) {
        Articles articles = articlesService.queryArticleById(id);
        return JsonResult.success(articles);
    }

    @RequestMapping(value = "/queryArticleOrderByDate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryArticleOrderByDate(@RequestParam("page") Integer page, @RequestParam(value = "size") Integer size) {
        Page<Articles> articles = articlesService.queryArticleOrderByDateByPage(page, size);
        return JsonResult.success(articles);
    }

    @RequestMapping(value = "/queryArticleByLikeByPage", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryArticleByLikeByPage(@RequestParam("index") String index,@RequestParam("page") Integer page, @RequestParam(value = "size") Integer size) {
        Page<Articles> articles = articlesService.queryArticleByLikeByPage(index,page, size);
        return JsonResult.success(articles);
    }

    @RequestMapping(value = "/getAdmireArticles", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getAdmireArticles(@RequestParam("page") Integer page, @RequestParam(value = "size") Integer size, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }
        Page<Admire> admires = articlesService.queryAdmirArticleById(userId, page, size);
        return JsonResult.success(admires);
    }

    @ApiOperation(value = "文章点赞", httpMethod = "POST")
    @RequestMapping(value = "/addArticleAdmire.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addArticleAdmire(@RequestParam("admiredId") String admiredId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }
        Admire admire = new Admire();
        admire.setAdmirId(UUID.randomUUID().toString());
        admire.setAdmirDate(DateFormatUtils.format(new Date(), Constants.DATE_PATTERN));
        admire.setUserId(userId);
        admire.setAdmiredId(admiredId);
        admire.setType(Constants.ARTICLE_TYPE);
        String number = admireDao.getAdmireNumberByAdmiredId(admiredId, userId);
        if (Integer.valueOf(number) > 0) {
            return new JsonResult(true, "您已经点赞，取消请在个人主页中操作", "操作成功");
        }
        Integer integer = articlesService.addAdmire(admire);
        if (integer > 0) {
            return JsonResult.success("操作成功");
        } else {
            return JsonResult.failed("操作失败");
        }
    }
    @ApiOperation(value = "点赞", httpMethod = "POST")
    @RequestMapping(value = "/addAdmire.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addAdmire(@RequestParam("admiredId") String admiredId,@RequestParam("type") String type,  HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }
        Admire admire = new Admire();
        admire.setAdmirId(UUID.randomUUID().toString());
        admire.setAdmirDate(DateFormatUtils.format(new Date(), Constants.DATE_PATTERN));
        admire.setUserId(userId);
        admire.setAdmiredId(admiredId);
        admire.setType(type);
        String number = admireDao.getAdmireNumber(admiredId, userId,type);
        if (Integer.valueOf(number) > 0) {
            return new JsonResult(true, "您已经点赞，取消请在个人主页中操作", "您已点过赞了");
        }
        Integer integer = articlesService.addAdmire(admire);
        if (integer > 0) {
            return JsonResult.success("操作成功","操作成功");
        } else {
            return JsonResult.failed("操作失败","操作失败");
        }
    }

    @RequestMapping(value = "/updateArticle.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateArticle(@RequestBody(required = true) Map<String, Object> map, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }
        String title = map.get("title").toString();
        String content = map.get("content").toString();
        String views = map.get("views").toString();
        String articleId = map.get("articleId").toString();
        if(StringUtils.isBlank(articleId)||StringUtils.isBlank(content)||StringUtils.isBlank(views)){
            return JsonResult.failed("参数不能为空");
        }
        Articles articles = new Articles();
        articles.setArticleId(articleId);
        articles.setArticleTitle(title);
        articles.setArticleDate(DateFormatUtils.format(new Date(), Constants.DATE_PATTERN));
        articles.setArticleViews(views);
        articles.setArticleContent(content);
        boolean b = articlesService.updateArticle(articles);
        if (b) {
            return JsonResult.success("操作成功");
        } else {
            return JsonResult.failed("操作失败");
        }

    }

    @RequestMapping(value = "/deleteAdmire.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteAdmire(@RequestParam("admiredId") String admiredId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }

        String number = admireDao.getAdmireNumberByAdmiredId(admiredId, userId);
        if (Integer.valueOf(number) > 0) {
            Integer integer = admireDao.deleteAdmire(admiredId, userId);
            if (integer > 0) {
                return JsonResult.success("取消成功");
            }
        }
        return JsonResult.failed("操作失败");

    }

    @RequestMapping(value = "/deleteArticle.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteArticle(@RequestParam("articleId") String admiredId) {
        boolean b = articlesService.deleteArticle(admiredId);
        if (b) {
            return JsonResult.success("删除成功");
        } else {
            return JsonResult.failed("操作失败");
        }
    }


}
