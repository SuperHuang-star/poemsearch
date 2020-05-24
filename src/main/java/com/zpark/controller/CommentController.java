package com.zpark.controller;

import com.zpark.entity.Comment;
import com.zpark.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/queryAllByPage")
    public Map<String,Object> queryAllByPage(Integer page,Integer rows){
        return commentService.findAll(page,rows);
    }

    @RequestMapping("/addComment")
    public void addComment(Comment comment){
        commentService.addComment(comment);
    }

    @RequestMapping("/delComment")
    public void delComment(Comment comment){
        commentService.delComment(comment);
    }

    @RequestMapping("/edit")
    public void edit(String oper, Comment comment){
        if("del".equals(oper)){
            commentService.delComment(comment);
        }
    }
}
