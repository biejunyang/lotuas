package com.lotuas.samples.mybatis.controller;

import com.lotuas.samples.mybatis.MainClass;
import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan(basePackageClasses = MainClass.class)
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> listUsers(){
        logger.debug("request form GET /usr start.....");
        return userDao.listPageUser(null, null, null);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        userDao.addUser(user);
        return user;
    }
}
