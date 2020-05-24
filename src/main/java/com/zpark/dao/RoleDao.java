package com.zpark.dao;

import com.zpark.entity.Role;

import java.util.List;

public interface RoleDao {
    //查看全部角色
    List<Role> queryRole(String id);
}
