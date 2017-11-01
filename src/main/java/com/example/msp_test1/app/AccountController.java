/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import com.example.msp_test1.app.model.User;
import com.example.msp_test1.app.model.UserRepository;
import com.example.msp_test1.app.service.UserService;
import com.example.msp_test1.app.service.validation.Unused;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static org.thymeleaf.spring4.util.FieldUtils.errors;

/**
 *
 * @author saku
 */
@Controller
@RequestMapping("accounts")
public class AccountController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserRepository userRepository;
    
    @ModelAttribute
    public AccountForm setupForm(){
        return new AccountForm();
    }
    
    @RequestMapping(path = "create", method = RequestMethod.GET)
    public String form(AccountForm accountForm, Model model) {

        model.addAttribute("AccountForm", accountForm);
        return "account/form";
    }
    
    @RequestMapping(path = "confirm", method = RequestMethod.POST)
    public String createConfirm(@Valid AccountForm accountForm, BindingResult result, Model model,
            RedirectAttributes redirectAttributes){
      
        if (result.hasErrors()){
            return "account/form";
      }
        model.addAttribute("AccountForm", accountForm);
        return "account/confirm";
    }
    
    @RequestMapping(path = "regist", method = RequestMethod.POST)
    public String regist(@Valid AccountForm accountForm, BindingResult result, Model model){
        if (result.hasErrors()){
            return "account/form";
      }
        userService.regist(accountForm);
        return "account/regist";
    }
    
//    @RequestMapping(path = "customers", method = RequestMethod.GET)
//    public String list (Model model){
//        List<User> users = userRepository.findAll();
//        model.addAttribute("users", users);
//        return "account/list";
//    }
    
    // リスト一覧 (pathのセンスはきにしない)
    @RequestMapping(path = "customers", method = RequestMethod.GET)
    public String list(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "account/list";
    }
    
    
//    @RequestMapping(path = "delete", method = RequestMethod.POST)
//    public String delete(@RequestParam Integer id){
//        userService.delete(id);
//        return "redirect:/accounts/create";
//    }
//  
    
    // いったん回避
//    @RequestMapping(path = "menu", method = RequestMethod.GET)
//    public String menu(Principal principal, Model model  ){
//        Authentication auth = (Authentication)principal;
//        User user = (User)auth.getPrincipal();
//        model.addAttribute("username", user.getUsername());
////        model.addAttribute("user", user);
//        return "menu";
//    }
    
    @RequestMapping(path = "info", method = RequestMethod.GET)
    public String info(Principal principal, Model model){
        Authentication auth = (Authentication)principal;
        User user = (User)auth.getPrincipal();
//        modelMap.addAttribute("username", userDetails.getUsername());
//        model.addAttribute("user", user);s
        model.addAttribute("name", user.getName());
        model.addAttribute("id", user.getId());
        model.addAttribute("mail", user.getMail());
        return "account/info";
    }
    
    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public String editForm(@RequestParam Integer id, AccountForm accountForm){
        User user = userService.findOne(id);
        BeanUtils.copyProperties(user, accountForm);
        return "account/edit";
    }
    
    @RequestMapping(path = "edit", params = "gotoTop")
    public String gotoTop() {
        return "redirect:/create";
    }
    
    
   
}
