package com.ylt.service;

import com.ylt.common.Page;
import com.ylt.entity.Comment;

import java.util.List;

public interface ICommentService {

    public boolean addComment(Comment articles);

    public Page<Comment> queryCommentByParent(Integer page, Integer size, String parentId);
}
