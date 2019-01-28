package com.ylt.service;



import com.ylt.entity.User;


public interface UserService {
    public User login(String name,String password);
    public User getUserById(String userId);

}
