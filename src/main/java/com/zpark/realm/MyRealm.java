package com.zpark.realm;

import com.zpark.dao.RoleDao;
import com.zpark.dao.UserDao;
import com.zpark.entity.Role;
import com.zpark.entity.User;
import com.zpark.utils.PermissionQc;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionQc permissionQc;
    @Autowired
    private RoleDao roleDao;
    //权限管理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String  principal = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = null;
        User login = userDao.login(new User(null, principal, null, null));
        if (login!=null){
            simpleAuthorizationInfo= new SimpleAuthorizationInfo();
            HashSet qc = permissionQc.qc(login.getId());
            List<Role> roles = roleDao.queryRole(login.getId());
            for (Object o : qc) {
                simpleAuthorizationInfo.addStringPermission((String) o);
            }
            for (Role role : roles) {
                simpleAuthorizationInfo.addRole(role.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        User login = userDao.login(new User(null, principal, null, null));
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if(login!=null){
            //  Md5Hash abcd = new Md5Hash("123456", "abcd", 1024);
            //String s = abcd.toHex();
            simpleAuthenticationInfo= new SimpleAuthenticationInfo(principal,login.getPassword(), ByteSource.Util.bytes(login.getSalt()),this.getName());
        }
        return simpleAuthenticationInfo;
    }
}
