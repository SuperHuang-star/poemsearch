package com.zpark.dao;

import com.zpark.entity.Permission;

import java.util.List;

public interface PermissionDao {
    //查看所有权利
    List<Permission> queryPermission(String id);
}
