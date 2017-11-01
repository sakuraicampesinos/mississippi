/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<NumberValid,String>{

@Override
public void initialize(NumberValid nv){

}

@Override
public boolean isValid(String in,ConstraintValidatorContext cxt){
if(in == null){
return false;
}

return in.matches("[0-9*]");
}
}
