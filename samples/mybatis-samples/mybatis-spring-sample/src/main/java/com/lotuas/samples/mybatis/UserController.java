package com.lotuas.samples.mybatis;

import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Log log= LogFactory.getLog(UserController.class);

    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> listUsers(){
        log.info("list users");
        log.warn("warning");
        return userDao.listPageUser(null, null, null);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        userDao.addUser(user);
        return user;
    }
}
