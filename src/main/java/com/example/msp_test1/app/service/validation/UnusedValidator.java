/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.service.validation;

import com.example.msp_test1.app.model.User;
import com.example.msp_test1.app.service.UserService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author saku
 */
@Component
public class UnusedValidator implements ConstraintValidator<Unused, String> {

    @Autowired
    UserService userService;

    public void initialize(Unused constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {

        User user = userService.findByMail(value);
        if(user == null){
            return true;
        }
        return false;
    }
}
