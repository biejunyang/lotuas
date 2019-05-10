package com.lotuas.samples.mybatis.dao;

import com.lotuas.samples.mybatis.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User getUser(int id);

    List<Map<String,Object>> listUser(User user);

    List<User> listPageUser(User user, Integer start, Integer limit);

    void addUser(User user);

    void addUsers(List<User> users);
}
