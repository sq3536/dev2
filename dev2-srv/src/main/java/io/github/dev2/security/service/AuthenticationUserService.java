package io.github.dev2.security.service;

import io.github.dev2.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("AuthenticationUserService")
public class AuthenticationUserService implements UserDetailsService
{

    @Autowired
    private SysUserService userService;


    @Override
    public UserDetails loadUserByUsername(String username){
        return userService.getByUsername(username);
    }




}
