/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import java.security.Principal;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author saku
 */
@Controller
public class WelcomeController {
    
    @RequestMapping("/")
    public String home(Principal principal){
        
    Authentication auth = (Authentication)principal;
    if(auth != null){
        return "redirect:menu";
    } 
        
        return "index";
    }
    
    @RequestMapping(path = "login_page", method = RequestMethod.GET)
    public String login_page(){
        return "login_page";
    }
    
    
}
