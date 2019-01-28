package com.ylt.dao;

import com.ylt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserDao {
    public List<User> getUserList();

    public Integer insertUser(User user);

    public void updateUser(User user);

    public void deleteUser(String userId);

    public User getUser(String id);
    public Integer getUserCount(String name);
    public Integer getEmailCount(String emil);

//    public User getUserById(String id);

    public User login(@Param("name")String name,@Param("password")String password);
}
