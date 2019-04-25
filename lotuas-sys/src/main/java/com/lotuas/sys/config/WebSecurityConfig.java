package com.lotuas.sys.config;

import com.lotuas.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//启用Spring Security安全认证机制
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;




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
         *      d、loginProcessingUrl(),自定义认证url
         *      e、successHandler()认证成功处理器，failureHandler()认证失败处理器
         *
         * 3、注销处理，WebSecurityConfigurerAdapter默认使用/logout注销用户，清空session。自定义注销配置：
         *      a、logoutUrl()：注销用户处理url，默认是为/logout
         *      b、logoutSuccessUrl():注销成功跳转页面，默认为/login?logout
         *      c、logoutSuccessHandler()：注销成功后处理器,设置后logoutSuccessUrl()将失效
         *      d、invalidateHttpSession()：使session失效
         *      e、deleteCookies()删除Cookie
         */
        http.authorizeRequests()
                .antMatchers("/", "/welcome", "/statics/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()

                //2、登录方式配置：使用表单提交的方式，认证用户,登录的页面，登录页不需认证
            .formLogin()
//                .loginPage("/login").permitAll()
//                .loginProcessingUrl("/login").permitAll()
//                .successHandler().failureHandler()
//                .usernameParameter("username").passwordParameter("password")
//                .defaultSuccessUrl("/")
//                .successForwardUrl("/login?success").permitAll()
//                .failureForwardUrl("/login?error").permitAll()
                .and()


                //3、注销配置
//            .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .logoutSuccessHandler()
//                .invalidateHttpSession(true)
//                .deleteCookies("username")
//                .permitAll()
            .httpBasic().and()
//            .addFilter()
//            .addFilterAfter()
//            .addFilterAt()
//            .addFilterBefore()
            ;



    }


    /**
     * 用户认证：
     *     1、In-Memory Authentication内存用户认证,从内容中获取用户信息，注意设置密码时，需要执行密码的加密格式
     *     2、JDBC Authentications数据源认证
     *     3、LDAP Authentication
     *     4、自定义认证方式，两种实现：
     *          a、spring容器中注入AuthenticationProvider对象
     *          b、spring容器中注入UserDetailsService对象
     *         注意只有当AuthenticationManagerBuilder对象没有使用其他认证方式时，以上两种自定义认证方式才能生效，
     *         并且AuthenticationProvider认证会覆盖UserDetailsService认证
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER").and()
//                .withUser("admin").password("{noop}admin").roles("ADMIN", "USER");

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }


    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return userService;
    }


    /**
     * 指定密码的加密类型
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }




    /**
     * 将内置的UserDetailsService实现类，注册Spring容器中
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
}
