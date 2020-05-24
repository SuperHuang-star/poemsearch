package com.zpark.dao;

import com.zpark.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    //查询全部
    List<User> queryAllByPage(@Param("start")Integer start,@Param("size")Integer size);

    //查询总条数
    Integer count();

    //查询单个
    User login(User user);

    //添加用户
    void addUser(User user);

    //修改用户
    void updateUser(User user);
}
