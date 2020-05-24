package com.zpark.controller;

import com.zpark.entity.User;
import com.zpark.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Map<String,Object> login(String username,String password){
        Map<String,Object> map = new HashMap<>();
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try{
                subject.login(token);
                map.put("message","登录成功");
            }catch (IncorrectCredentialsException e){
                e.printStackTrace();
                map.put("message","密码错误");

            }catch (UnknownAccountException e){
                e.printStackTrace();
                map.put("message","用户名不存在");
            }
        return map;
    }

    @RequestMapping("/logout")
    public Map<String,Object> logout(){
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        map.put("message","退出成功");
        return map;
    }

    @RequestMapping("/queryAllByPage")
    public Map<String,Object> queryAllByPage(Integer page,Integer rows){
        Map<String, Object> all = userService.findAll(page, rows);
        return all;
    }

    @RequestMapping("/edit")
    public Map<String,Object> edit(String oper, User user){
        if("add".equals(oper)){
            userService.addUser(user);
        }else if ("edit".equals(oper)){
            userService.updateUser(user);
        }
        return null;
    }
}
