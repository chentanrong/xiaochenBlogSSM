package com.ylt.service.impl;


import com.ylt.dao.UserDao;
import com.ylt.entity.User;
import com.ylt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User login(String name, String password) {
        User dbuser = userDao.login(name, password);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("userInfo",dbuser);
        return dbuser;
    }

    @Override
    public User getUserById(String userId) {

        return  userDao.getUser(userId);
    }

}
