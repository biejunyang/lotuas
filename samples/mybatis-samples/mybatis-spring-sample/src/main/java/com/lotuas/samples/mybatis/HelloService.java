package com.lotuas.samples.mybatis;

import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloService {

    @Autowired
    private UserDao userDao;

    public List<User> listUser(){
        return userDao.listPageUser(null, null, null);
    }
}
