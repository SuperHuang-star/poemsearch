package com.zpark.dao;

import com.zpark.entity.Comment;
import com.zpark.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    //查询全部
    List<Comment> queryAll();

    //后台分页查询全部
    List<User> queryAllByPage(@Param("start")Integer start, @Param("size")Integer size);

    //查询总条数
    Integer count();

    //添加评论
    void addComment(Comment comment);

    //删除评论
    void delComment(Comment comment);
}
