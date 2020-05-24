package com.zpark.conf;

import com.zpark.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroFilterConf {
    //shiro过滤器
    /**
     *  定义拦截URL权限，优先级从上到下
     *      1). anon  : 匿名访问，无需登录
     *      2). authc : 登录后才能访问
     *      3). logout: 登出
     *      4). roles : 角色过滤器
     *
     *  URL 匹配风格
     *      1). ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/；
     *      2). *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1；
     *      2). **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/poem/**","anon");
        map.put("/dic/**","anon");
        map.put("/es/**","anon");
        map.put("/comment/**","anon");
        map.put("/category/**","anon");
        map.put("/back/login.jsp","anon");
        map.put("/user/login","anon");
        map.put("/front/*","anon");
        map.put("/boot/**","anon");
        map.put("/jqgrid/**","anon");
        map.put("/images/**","anon");
//        map.put("/user/logout","anon");
        map.put("/**","authc");
//        shiroFilterFactoryBean.setLoginUrl("/back/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/back/login.jsp");        // 登录的路径
        shiroFilterFactoryBean.setSuccessUrl("/back/main.jsp");    // 登录成功后跳转的路径
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");    // 验证失败后跳转的路径
        return shiroFilterFactoryBean;
    }

    //安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(MyRealm myRealm, MemoryConstrainedCacheManager memoryConstrainedCacheManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        defaultWebSecurityManager.setCacheManager(memoryConstrainedCacheManager);
        return defaultWebSecurityManager;
    }

    //自定义领域
    @Bean
    public MyRealm myRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    //凭证匹配器
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
    @Bean
    public MemoryConstrainedCacheManager memoryConstrainedCacheManager(){
        MemoryConstrainedCacheManager memoryConstrainedCacheManager = new MemoryConstrainedCacheManager();
        return memoryConstrainedCacheManager;
    }



}
