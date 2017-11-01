/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.model;

import com.example.msp_test1.app.service.validation.NumberValid;
import com.example.msp_test1.app.service.validation.Unused;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * ここログインのユーザも処理する
 */
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotBlank
    @Column(name = "name")
    private String name;
    
    @NotBlank
    @Column(name = "mail")
//    @Unused
    private String mail;
    
    @NotBlank
//    @NumberValid
    @Column(name = "password")
    private String password;
    
    @Column(name = "icon_url")
    private String icon_url;
    
    @Column(name = "created")
    private Date created;
    
//    ここタスクなので OneToManyする
    @OneToMany(mappedBy = "user", cascade= {CascadeType.ALL}, fetch=FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Task> taskLists;

    public List<Task> getTaskList() {
        return taskLists;
    }

    public void setTaskList(List<Task> taskLists) {
        this.taskLists = taskLists;
    }
    
//    ここまでタスク
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
//    public User() {
//        super("INVALID", "INVALID", false, false, false, false, new ArrayList<GrantedAuthority>());
//    }
//    
//    public User(String username, String password, boolean enabled, boolean accountNonExpired,
//                boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//        setUsername(username);
//        setPassword(password);  
//        setEnabled(enabled);
//    }

    
    /* (非 Javadoc)
    * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
 
    /* (非 Javadoc)
    * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
    */
    @Override
    public String getUsername() {
        return this.name;
    }
 
    /* (非 Javadoc)
    * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
    */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    /* (非 Javadoc)
    * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
    */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
