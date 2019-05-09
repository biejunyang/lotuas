package com.lotuas.samples.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.lotuas.samples.mybatis.dao.UserDao;
import com.lotuas.samples.mybatis.domain.User;
import com.lotuas.starter.spring.web.SystemContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MybatisTest {


    private static SqlSessionFactory sqlSessionFactory;

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
//
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//
//        Configuration configuration = new Configuration(environment);
//        configuration.setLazyLoadingEnabled(true);
//        configuration.addMappers("com.lotuas.samples.mybatis.dao");
//        configuration.getTypeAliasRegistry().registerAlias("user", User.class);
////        configuration.addMapper(UserDao.class);
//        sqlSessionFactory=sqlSessionFactoryBuilder.build(configuration);
    }


    @Test
    public void test(){
        SqlSession session=sqlSessionFactory.openSession();
//        User user=session.selectOne("com.lotuas.samples.mybatis.dao.UserDao.getUser", 1);
        UserDao userDao=session.getMapper(UserDao.class);
        User user=userDao.getUser(1);
        System.out.println(user);

    }
}
