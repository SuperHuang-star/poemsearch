package com.zpark.utils;

import com.zpark.dao.PermissionDao;
import com.zpark.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
@Component
public class PermissionQc {
    @Autowired
    private PermissionDao permissionDao;
    //权限去重
    public HashSet qc(String id){

        List<Permission> permissions = permissionDao.queryPermission(id);
        HashSet<String> objects = new HashSet<>();
        for (Permission permission : permissions) {
            objects.add(permission.getResourceHref());
        }
        return objects;
    }
}
