package com.zpark.service;

import com.zpark.entity.Comment;

import java.util.Map;

public interface CommentService {
    //查询全部
    Map<String,Object> findAll(Integer page, Integer rows);

    //添加评论
    void addComment(Comment comment);

    //删除评论
    void delComment(Comment comment);
}
