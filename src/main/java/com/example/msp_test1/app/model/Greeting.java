/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.model;

/**
 *
 * @author saku
 */
public class Greeting {
    private String content;
    
    public Greeting(){
    
    }

    public Greeting(String content){
        this.content = content;
    }
    
    public String getContent(){
        return content;
    }
}
