package com.ylt.service.impl;

import com.ylt.common.Page;
import com.ylt.dao.CommentDao;
import com.ylt.entity.Articles;
import com.ylt.entity.Comment;
import com.ylt.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public boolean addComment(Comment comment) {
        commentDao.addComment(comment);
        return true;
    }

    @Override
    public Page<Comment> queryCommentByParent(Integer page,Integer size,String parentId) {
        Page<Comment> page1=new Page<Comment>();
        page1.setCurrentPage(page);
        page1.setPageSize(size);
        List<Comment> list = commentDao.queryCommentByParentByPage(page1,parentId);
        page1.setItems(list);
        return page1;
    }
}
