package com.zpark.service;

import com.zpark.entity.Poem;

import java.util.Map;
import java.util.Set;

public interface PoemService {
    //后台分页查询
    Map<String,Object> queryByPage(Integer page,Integer rows);

    //查询全部条数
    Integer count();

    //查询全部作者
    Set<String> queryAuthor();

    //查询单个
    Poem findOne(Poem poem);

    //后台增加
    void addPoem(Poem poem);

    //后台删除
    void delPoem(Poem poem);

    //后台修改
    void updatePoem(Poem poem);


}
