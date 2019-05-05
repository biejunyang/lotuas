package com.lotuas.auth.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DomainUserDetailsService implements UserDetailsService {


    /**
     * 根据用户名查找账户信息并返回用户信息实体
     * @param username 用户名
     * @return 用于身份认证的 UserDetails 用户信息实体
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("zhangsan", "123456", AuthorityUtils.createAuthorityList("ADMIN", "NORMAL"));
    }
}
