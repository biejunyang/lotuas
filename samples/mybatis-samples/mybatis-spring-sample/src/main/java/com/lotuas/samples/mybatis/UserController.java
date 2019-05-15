package com.lotuas.samples.mybatis;

import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> listUsers(){
        Log logger = LogFactory.getLog(UserController.class);
        Logger logger2= LogManager.getLogger(UserController.class);
        logger.info("hello list user {}");
        logger2.info("cxccxcxcx{},{}", "xcxc", "xcxcx");
        return userDao.listPageUser(null, null, null);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        userDao.addUser(user);
        return user;
    }
}
