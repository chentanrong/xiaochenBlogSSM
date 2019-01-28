package com.ylt.dao;

import com.ylt.common.Page;
import com.ylt.entity.Comment;

import java.util.List;

public interface CommentDao {

    public boolean addComment(Comment comment);

    public List<Comment> queryCommentByParentByPage(Page page,String id);


}
