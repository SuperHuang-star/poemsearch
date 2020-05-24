package com.zpark.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//远程热词操作
@RestController
@RequestMapping("/dic")
public class DicController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //获取全部热词
    @RequestMapping("/findAll")
    public List<Object> findAll(HttpServletRequest request) throws Exception{
        String realPath = request.getServletContext().getRealPath("/");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(realPath, "ini.dic")));
        List<Object> list = new ArrayList<>();
        while(true){
            String keywords = bufferedReader.readLine();
            if(keywords==null)break;
            list.add(keywords);
        }
        return list;}
    //加载远程热词
    @RequestMapping("/remote")
    public String remote(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String realPath = request.getServletContext().getRealPath("/");
        String s = FileUtils.readFileToString(new File(realPath, "ini.dic"));
        response.setDateHeader("Last-Modified",System.currentTimeMillis());
        response.setHeader("ETag",s);
        return s;}
    //添加远程热词
    @RequestMapping("/addOne")
    public Map<String,Object> addOne(HttpServletRequest request,String keyword){
        String realPath = request.getServletContext().getRealPath("/");
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            String s = FileUtils.readFileToString(new File(realPath, "ini.dic"));
            if(!s.contains(keyword)){
                FileUtils.write(new File(realPath, "ini.dic"), StringUtils.trimAllWhitespace(keyword)+"\r\n","UTF-8",true);
                map.put("success",true);
            }else{
                throw new RuntimeException("此热词已存在，请勿重复添加！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("sucess",false);
        }
        return map;}
    //删除热词
    @RequestMapping("/delOne")
        public Map<String,Object> delOne(HttpServletRequest request,String keyword){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            String realPath = request.getServletContext().getRealPath("/");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(realPath, "ini.dic")));
            StringBuilder stringBuilder = new StringBuilder();
            while(true){
                String s = bufferedReader.readLine();
                if(s==null)break;
                if(!keyword.equals(s)){
                    stringBuilder.append(s).append("\r\n");
                }
            }
            FileUtils.write(new File(realPath, "ini.dic"),stringBuilder.toString(),"UTF-8",false);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;}
    //获取热搜榜
    @RequestMapping("/findRedisKeywords")
    public Set<ZSetOperations.TypedTuple<String>> findRedisKeywords(){
        Set<ZSetOperations.TypedTuple<String>> keywords = stringRedisTemplate.opsForZSet().reverseRangeWithScores("keywords", 0, 10);
        return keywords;
    }
}
