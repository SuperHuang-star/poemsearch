package com.zpark.service;

import com.zpark.entity.Poem;

import java.util.List;
import java.util.Map;

public interface EsService {
    //重建索引
    Map<String,Object> flushIndex();
    //重建索引数据
    Map<String, Object> createIndex();
    //关键词检索
    List<Poem> findByKeywords(String content,String type,String author);
    //清空数据
    Map<String,Object> delAll();

}
