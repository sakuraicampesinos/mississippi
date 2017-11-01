/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import com.example.msp_test1.app.model.User;
import com.example.msp_test1.app.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author saku
 */
@Component
public class JpaUserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        User user = null;
        User user = userRepository.findByName(name);
        
        user.setId(user.getId());
        user.setMail(user.getMail());
        
        return user;
    }
}
