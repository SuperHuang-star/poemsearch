package com.zpark.service.impl;

import com.zpark.dao.UserDao;
import com.zpark.entity.User;
import com.zpark.service.UserService;
import com.zpark.utils.RandomString;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> findAll(Integer page,Integer rows) {
        Map<String,Object> map  = new HashMap<String,Object>();
        Integer count = userDao.count();
        Integer total = count%rows==0?count/rows:count/rows+1;
        Integer start = (page-1)*rows;
        List<User> users = userDao.queryAllByPage(start, rows);
        map.put("page",page);
        map.put("rows",users);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        String s = UUID.randomUUID().toString();
        //生成盐（部分，需要存入数据库中）
        String salt = RandomString.getRandomString(4);
        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
        String password = new SimpleHash("MD5", user.getPassword(), salt, 1024).toHex();
        user.setPassword(password).setSalt(salt).setId(s);
        System.out.println(user.getPassword()+"===="+user.getSalt());
        userDao.addUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        //生成盐（部分，需要存入数据库中）
        String salt = RandomString.getRandomString(4);
        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
        String password = new SimpleHash("MD5", user.getPassword(), salt, 1024).toHex();
        user.setPassword(password).setSalt(salt);
        System.out.println(user.getPassword()+"===="+user.getSalt());
        userDao.updateUser(user);
    }
}
