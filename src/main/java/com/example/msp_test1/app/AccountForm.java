/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import com.example.msp_test1.app.service.validation.Unused;
import com.example.msp_test1.app.service.validation.UnusedName;
import java.io.Serializable;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author saku
 */
public class AccountForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Email
    @NotBlank(message="メールアドレスを入力してください")
    @Unused
    private String mail;
    
    @UnusedName
    @Length(min=1, max=20, message="20文字以内で入力してください")
    private String name;
    
    @NotBlank(message="パスワード入力してください")
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
}
