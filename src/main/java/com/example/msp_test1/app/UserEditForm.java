/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import com.example.msp_test1.app.service.validation.UnusedName;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author saku
 */
public class UserEditForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @UnusedName
    @Length(min = 1, max = 20, message = "20文字以内で入力してください")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
