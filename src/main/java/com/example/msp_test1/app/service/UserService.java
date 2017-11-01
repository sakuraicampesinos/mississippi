/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.service;

import com.example.msp_test1.app.AccountForm;
import com.example.msp_test1.app.FileUploadForm;
import com.example.msp_test1.app.MenuController;
import com.example.msp_test1.app.UserEditForm;
import com.example.msp_test1.app.model.User;
import com.example.msp_test1.app.model.UserRepository;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.time.*;
import static javassist.CtMethod.ConstParameter.integer;

import javax.transaction.Transactional;
import org.apache.log4j.Logger;
//import org.hibernate.validator.constraints.ParameterScriptAssert.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author saku
 */
@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResourceLoader resourceLoader;

    public User regist(AccountForm accountForm) {
        User user = new User();
        user.setName(accountForm.getName());
        user.setMail(accountForm.getMail());

        //パスワードのエンコードを実施
        String encodePassword = passwordEncoder.encode((accountForm.getPassword()));
        user.setPassword(encodePassword);

        Date date = new Date();
        user.setCreated(date);
        return userRepository.save(user);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

//    public User findByMail(String mail){
//        User user = userRepository.findByMail(mail);
//        return user;
//    }
    public User findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    public List<User> search(String mail) {
        List<User> result = userRepository.findAll();
        return result;
    }

    public void userEdit(UserEditForm userEditForm, Integer id) {
        User user = userRepository.findOne(id);
        user.setName(userEditForm.getName());
        userRepository.save(user);
    }

//    private static final Logger log = Logger.getLogger(MenuController.class);

    public void userProfile_imgUpload(FileUploadForm fileUploadForm, Principal principal) throws IOException{

        Authentication auth = (Authentication) principal;
        User user = (User) auth.getPrincipal();
//        log.info(user);

//        System.out.println("Fetching file");
        MultipartFile multipartFile = fileUploadForm.getFileData();
//        log.info(multipartFile.getSize());
        String fileName = multipartFile.getOriginalFilename();
//        log.info(fileName);
        String path = new File(".").getAbsoluteFile().getParent();
//        log.info(path);
        String path2 = System.getProperty("user.dir");
//        log.info(path2);
        FileCopyUtils.copy(fileUploadForm.getFileData().getBytes(), new File(fileUploadForm.getFileData().getOriginalFilename()));
     
        String imgName = fileUploadForm.getFileData().getOriginalFilename();
        String userName = user.getName();

        Resource resource = this.resourceLoader.getResource("s3://sakuraicamp/" + userName + "/" + imgName);

//        log.info(resource);
        WritableResource writableResource = (WritableResource) resource;
//        log.info(writableResource);
        try (OutputStream output = writableResource.getOutputStream()) {
            byte[] bytes = fileUploadForm.getFileData().getBytes();
            output.write(bytes);
        } catch (IOException e) {

        }
    }

}
