package com.lotuas.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                List<SimpleGrantedAuthority> authorities=new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
                return new User("admin", "admin", authorities);
            }
        }).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//                List<SimpleGrantedAuthority> authorities=new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("ADMIN"));
//                return new User("admin", "admin", authorities);
//            }
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
}
