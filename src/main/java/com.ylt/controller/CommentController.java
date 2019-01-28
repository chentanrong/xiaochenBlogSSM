package com.ylt.controller;

import com.ylt.common.JsonResult;
import com.ylt.common.Page;
import com.ylt.config.Constants;
import com.ylt.entity.Comment;
import com.ylt.service.ICommentService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(tags = "评论模块")
@Controller
public class CommentController {


    @Resource
    ICommentService articlesService;

    @RequestMapping(value = "/addComment.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addComment( @RequestParam("content") String content, @RequestParam(required = false ,value = "imge") String imge,
                                  @RequestParam("parentId") String parentId, HttpServletRequest request) {
        String userId = (String)request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setCommentContent(content);
        comment.setCommentImge(imge);
        comment.setParentCommentId(parentId);
        comment.setCommentDate(DateFormatUtils.format(new Date(), Constants.DATE_PATTERN));
        comment.setCommentId(UUID.randomUUID().toString());
        return JsonResult.success(articlesService.addComment(comment));
    }


    @RequestMapping(value = "/queryCommentByParent", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryCommentByParent(@RequestParam("page") Integer page,@RequestParam("size") Integer size,@RequestParam("parentId") String parentId) {
        Page<Comment> articles = articlesService.queryCommentByParent(page,size,parentId);
        return JsonResult.success(articles);
    }




}
