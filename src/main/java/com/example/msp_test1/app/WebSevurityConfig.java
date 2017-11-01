/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author saku
 */
@Configuration
//@EnableWebMvcSecurity
@EnableWebSecurity
public class WebSevurityConfig extends WebSecurityConfigurerAdapter{
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                    .antMatchers("/","/accounts/create","/accounts/confirm","/accounts/regist").permitAll()
                    .antMatchers("/","/accounts/create","/accounts/confirm","/accounts/regist").permitAll()
//                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
//                            .loginPage("/login_page")
                            .loginProcessingUrl("/login_page")
                            .loginPage("/")

                            .defaultSuccessUrl("/menu", true)
                            .failureUrl("/")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .permitAll()
                            .and()
                            .rememberMe();
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/css/**", "/bootstrap/**","/js/**", "/images/**");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter{
        
        @Autowired
        JpaUserDetailsServiceImpl userDetailsService;
        
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
    }
    
}