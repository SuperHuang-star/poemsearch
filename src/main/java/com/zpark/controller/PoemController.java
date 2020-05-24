package com.zpark.controller;

import com.zpark.entity.Poem;
import com.zpark.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/poem")
public class PoemController {
    @Autowired
    private PoemService poemService;

    @RequestMapping("/edit")
    public Map<String,Object> edit(String oper, Poem poem){
        if("add".equals(oper)){
            poemService.addPoem(poem);
        }else if("del".equals(oper)){
            poemService.delPoem(poem);
        }else if ("edit".equals(poem)){
            poemService.updatePoem(poem);
        }
        return null;
    }

    @RequestMapping("/queryByPage")
    public Map<String,Object> queryByPage(Integer page,Integer rows){
        Map<String, Object> stringObjectMap = poemService.queryByPage(page, rows);
        return stringObjectMap;
    }

    @RequestMapping("/queryAuthor")
    public Set<String> queryAuthor(){
        return poemService.queryAuthor();
    }
}
