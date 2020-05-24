package com.zpark.controller;

import com.zpark.entity.Poem;
import com.zpark.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private EsService esService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/flushIndex")
    public Map<String,Object> flushIndex(){
        return esService.flushIndex();
    }


    @RequestMapping("/createIndex")
    public Map<String,Object> createIndex(){
        return esService.createIndex();
    }

    @RequestMapping("/delAll")
    public Map<String,Object> delAll(){
        return esService.delAll();
    }

    @RequestMapping("/findByKeywords")
    public List<Poem> findByKeywords(String content,String type,String author){
        //放入redis
        if(!StringUtils.isEmpty(content)){
            stringRedisTemplate.opsForZSet().incrementScore("keywords",content,0.1);
        }
        if("所有".equals(type))type=null;
        if("所有".equals(author))author=null;
        return esService.findByKeywords(content,type,author);
    }
}
