package com.zpark.service;

import com.zpark.entity.User;

import java.util.Map;

public interface UserService {
    //查询全部
    Map<String,Object> findAll(Integer page,Integer rows);

    //查询单个
    User login(User user);

    //添加用户
    void addUser(User user);

    //修改用户
    void updateUser(User user);
}
