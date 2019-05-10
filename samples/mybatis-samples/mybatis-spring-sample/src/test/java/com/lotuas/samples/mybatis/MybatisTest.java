package com.lotuas.samples.mybatis;

import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MybatisTest {


    private static SqlSessionFactory sqlSessionFactory;

    private SqlSession session;
    private UserDao userDao;

    @BeforeClass
    public static void init() throws IOException {
        // 使用mybatis-config.xml配置文件构建SqlSessionFactory
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        sqlSessionFactory= sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"));


        //使用纯Java API构建SqlSessionFactory
//        DruidDataSource dataSource=new DruidDataSource();
//        dataSource.setUrl("jdbc:mysql://47.97.177.132:3306/lotuas?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
////        dataSource.setDefaultAutoCommit(false);
//
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.setLazyLoadingEnabled(true);
////        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.getTypeAliasRegistry().registerAliases("com.lotuas.samples.mybatis.domain");
//        configuration.addMappers("com.lotuas.samples.mybatis.dao");
//        sqlSessionFactory=sqlSessionFactoryBuilder.build(configuration);
    }


    @Before
    public void initMapper(){
        this.session=sqlSessionFactory.openSession(true);
        this.userDao=session.getMapper(UserDao.class);
    }



    @Test
    public void testGetUser(){
        User user=session.selectOne("com.lotuas.samples.mybatis.dao.UserDao.getUser", 1);
        System.out.println(user);
        System.out.println(userDao.getUser(2));
    }

    @Test
    public void testListUser(){
        List<Map<String, Object>> users=session.selectList("com.lotuas.samples.mybatis.dao.UserDao.listUser", null);
        System.out.println(users);
        System.out.println(userDao.listPageUser(null, null, null));
    }


    @Test
    public void testAddUser(){
        User user=new User();
        user.setName("王五");
        user.setAge(22);
        user.setBirthday(new Date());
        user.setSex((byte)1);
        user.setWeight(22.51);
        user.setOrganId(2);
        user.setCreateTime(new Date());
        userDao.addUser(user);
        System.out.println(user);
    }

    @Test
    public void testAddUsers(){
        List<User> users=new ArrayList<User>();
        for(int i=0; i< 10; i++){
            User user=new User();
            user.setName("王五"+i);
            user.setAge(10+i);
            user.setBirthday(new Date());
            user.setSex((byte)1);
            user.setWeight(22.51);
            user.setOrganId(i);
            user.setCreateTime(new Date());
            users.add(user);
        }
        userDao.addUsers(users);
        for(User user: users){
            System.out.println(user);
        }

    }
}
