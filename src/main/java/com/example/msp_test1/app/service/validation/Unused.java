/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
 
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
 
@Documented
@Constraint(validatedBy = {UnusedValidator.class}) 
@Target({METHOD,FIELD}) 
@Retention(RUNTIME)
public @interface Unused {
 
    String message() default "{すでに利用されているメールアドレスです}"; 
    
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    @Target({METHOD,FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Unused[] value(); 
    }
}