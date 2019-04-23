package com.lotuas.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置需要认证的路径、认证的方式、登录、登录
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * Spring Securtiry安全机制默认配置：
         *  1、所有请求都需要认证
         *  2、使用表单认证的方式
         *  3、使用内置的基础http表单认证方式：
         *      a：登录页面url：GET /login
         *      b：登录认证url：POST /login
         *      c：登录失败跳转url：GET /login?error
         *      d：注销确认页面：GET /logout
         *      e：注销登录：POST /logout
         *      f：注销成功页面：GET /login?logout
         */
//        super.configure(http);
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .and()
//            .httpBasic();


        /**
         * 自定义Spring Security安全配置:
         * 1、认证请求配置,authorizeRequests()方法后配置，如：
         *      a、路径"/"，"/home"以及"/statics"开头的URL允许被访问
         *      b、"/admin"开头的URL，访问认证时登录用户需要有角色“ROLE_ADMIN”，注意默认角色名要以"ROLE_"开头
         *      c、"/db"开头的URL，访问认证时登录用户需要同时拥有角色“ROLE_ADMIN”和“ROLE_DBA”，注意默认角色名要以"ROLE_"开头
         *      d、其他url都需要登录认证
         *
         * 2、认证方式使用表单认证方式：
         *      a、.loginPage()自定义登录页面路径设置，注意自定义登录页面不需要认证
         *      b、用户，密码请求参数设置,默认是username,password
         *      c、登录失败跳转设置failureForwardUrl()，默认为/login?error，注意失败页面不用认证
         *
         */
        http.authorizeRequests()
                .antMatchers("/", "/welcome", "/statics/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()

                //2、登录方式配置：使用表单提交的方式，认证用户,登录的页面，登录页不需认证
            .formLogin()
                .loginPage("/login").permitAll()
//                .usernameParameter("username").passwordParameter("password")
//                .defaultSuccessUrl("/")
//                .successForwardUrl("/login?success")
                .failureForwardUrl("/login?error").permitAll()
                .and()


                //3、注销配置
            .logout()
                .permitAll();


    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}
