/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.service;

import com.example.msp_test1.app.TaskForm;
import com.example.msp_test1.app.model.Task;
import com.example.msp_test1.app.model.TaskRepository;
import com.example.msp_test1.app.model.User;
import com.example.msp_test1.app.model.UserRepository;
import java.lang.reflect.Field;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import static org.aspectj.weaver.MemberImpl.field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 *
 * @author saku
 */
@Service
@Transactional
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    
    @Autowired
    UserRepository userRepository;
    

    /*
    タスク登録
     */
    public Task taskRegister(TaskForm taskForm, Principal principal) {
        Task task = new Task();
        //ユーザアカウント取得
        Authentication auth = (Authentication) principal;
        User users = (User) auth.getPrincipal();
        task.setUser(users);

        task.setTitle(taskForm.getTitle());
        task.setContent(taskForm.getContent());

        //時刻系の処理
        Date date = new Date();
        task.setCreate_date(date);

        try {
            String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String sd = taskForm.getStart_date();
            String mic = new SimpleDateFormat("ss.SSS").format(date);

            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss.SSS");
            Date startDate = sdFormat.parse(str + " " + sd + ":" + mic);
            task.setStart_date(startDate);

            String ed = taskForm.getEnd_date();
            Date endDate = sdFormat.parse(str + " " + ed + ":" + mic);
            task.setEnd_date(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        task.setDone(Boolean.FALSE);

        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void taskDelete(Integer id) {
        taskRepository.delete(id);
    }

    public void taskDone(Integer id) {
        Task task = taskRepository.findOne(id);
        task.setDone(Boolean.TRUE);
        taskRepository.save(task);
    }
    
    public User findByUser(Integer id){
        return userRepository.findOne(id);
    }

    public List<Task> findByMyTask(@Param("u_id") int u_id) {
        return taskRepository.findByMyTask(u_id);
    }

    public List<Task> findByTodayTasks(@Param("u_id") int u_id) {
        return taskRepository.findByTodayTask(u_id);
    }

    public Task findByNowTask(@Param("u_id") int u_id) {
        return taskRepository.findByNowTask(u_id);
    }

    public List<Task> findByOtherTask(@Param("u_id") int u_id) {
        return taskRepository.findByOtherTask(u_id);
    }

}
