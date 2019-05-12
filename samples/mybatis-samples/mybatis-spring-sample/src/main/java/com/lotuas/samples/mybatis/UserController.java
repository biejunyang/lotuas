package com.lotuas.samples.mybatis;

import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
//
//    Logger logger = LoggerFactory.getLogger(UserController.class);
//

    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> listUsers(){
        return userDao.listPageUser(null, null, null);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        userDao.addUser(user);
        return user;
    }
}
