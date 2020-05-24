package com.zpark.service.impl;

import com.zpark.dao.CommentDao;
import com.zpark.entity.Comment;
import com.zpark.entity.User;
import com.zpark.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String,Object> map  = new HashMap<String,Object>();
        Integer count = commentDao.count();
        Integer total = count%rows==0?count/rows:count/rows+1;
        Integer start = (page-1)*rows;
        List<User> users = commentDao.queryAllByPage(start, rows);
        map.put("page",page);
        map.put("rows",users);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    public void addComment(Comment comment) {
        comment.setTime(new Date()).setId(UUID.randomUUID().toString());
        commentDao.addComment(comment);
    }

    @Override
    public void delComment(Comment comment) {
        commentDao.delComment(comment);
    }
}
