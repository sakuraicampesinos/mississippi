/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import com.example.msp_test1.app.model.Task;
import com.example.msp_test1.app.model.TaskRepository;
import com.example.msp_test1.app.model.User;
import com.example.msp_test1.app.model.UserRepository;
import com.example.msp_test1.app.service.TaskService;
import com.example.msp_test1.app.service.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import static org.springframework.http.RequestEntity.method;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 *
 * @author saku
 */
@Controller
//@RequestMapping(path = "menu")
public class MenuController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @ModelAttribute
    public TaskForm setupForm() {
        return new TaskForm();
    }

    /**
     * ログインしているユーザの直近のタスク1件を表示
     *
     * @param principal
     * @param taskForm
     * @param model
     * @return
     */
    @RequestMapping(path = "menu", method = RequestMethod.GET)
    public String menu(Principal principal, TaskForm taskForm, Model model) {
        Authentication auth = (Authentication) principal;
        User user = (User) auth.getPrincipal();
        String u_name = user.getName();
        int u_id = user.getId();
        Task task = taskService.findByNowTask(u_id);
        List<Task> othertasks = taskService.findByOtherTask(u_id);
        model.addAttribute("u_name", u_name);

        if (task != null) {
            model.addAttribute("task", task);
        }
        model.addAttribute("othertasks", othertasks);
        model.addAttribute("TaskForm", taskForm);
        return "menu";
    }

    /**
     * タスク登録後、メニュー画面へ遷移する
     *
     * @param taskForm
     * @param principal
     * @return
     */
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String register(TaskForm taskForm, Principal principal) {

        taskService.taskRegister(taskForm, principal);
        return "redirect:menu";
    }

    //とりあえず登録タスク全部リスト
    @RequestMapping(path = "tasklist", method = RequestMethod.GET)
    public String tasklist(Model model) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "tasklist";
    }

    /**
     * ログインユーザが今日登録したタスクを表示させる
     *
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping(path = "todaytask", method = RequestMethod.GET)
    public String todaytask(Principal principal, Model model) {
        Authentication auth = (Authentication) principal;
        User user = (User) auth.getPrincipal();
        int u_id = user.getId();
        List<Task> tasks = taskService.findByTodayTasks(u_id);
        model.addAttribute("tasks", tasks);
        return "todaytask";
    }

    /**
     * 一番最近のタスクを1件表示
     *
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping(path = "nowtask", method = RequestMethod.GET)
    public String nowtask(Principal principal, Model model) {
        Authentication auth = (Authentication) principal;
        User user = (User) auth.getPrincipal();
        int u_id = user.getId();
        Task task = taskService.findByNowTask(u_id);
        model.addAttribute("task", task);
        return "nowtask";
    }

    @RequestMapping(path = "help", method = RequestMethod.GET)
    public String help(Model model) {
        return "help";
    }

    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public String taskDelete(@RequestParam Integer id) {
        taskService.taskDelete(id);
        return "redirect:menu";
    }

    @RequestMapping(path = "done", method = RequestMethod.POST)
    public String taskDone(@RequestParam Integer id) {
        taskService.taskDone(id);
        return "redirect:menu";
    }

    @RequestMapping(path = "myProfile", method = RequestMethod.GET)
    public String profile(Principal principal, UserEditForm userEditForm, Model model) {
        Authentication auth = (Authentication) principal;
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("UserEditForm", userEditForm);
        return "myProfile";
    }

//    @RequestMapping(path = "profileEdit", method = RequestMethod.POST)
//    public String profileEdit(@Valid @ModelAttribute UserEditForm userEditForm, BindingResult result, @RequestParam Integer id){
//        userService.userEdit(userEditForm,id);
//        
//        if (result.hasErrors()){
//            return "myProfile";
//      }
//        return "redirect:myprofile";
//    }
//    プロフィールのエディット画面へ
    @RequestMapping(path = "profileEdit", method = RequestMethod.POST)
    public String profileEdit(@ModelAttribute UserEditForm userEditForm, @RequestParam Integer id) {
        userService.userEdit(userEditForm, id);
        return "redirect:menu";
    }

//    画像だけをアップロードして変更する画面
    @RequestMapping(path = "custom_image", method = RequestMethod.GET)
    public String custom_image() {
        return "custom_image";
    }

    @RequestMapping(path = "profile_upload", method = RequestMethod.GET)
    public String profile_upload() {
        return "profile_upload";
    }
    
    private static final Logger log = Logger.getLogger(MenuController.class); 

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(Principal principal,FileUploadForm fileUploadForm) throws IOException {
        
//        System.out.println("Fetching file");
//        MultipartFile multipartFile = fileUploadForm.getFileData();
//        log.info(multipartFile.getSize());
//        String fileName = multipartFile.getOriginalFilename();
//        log.info(fileName);
//        String path = new File(".").getAbsoluteFile().getParent();
//        log.info(path);
//        String path2 = System.getProperty("user.dir");
//        log.info(path2);
//        FileCopyUtils.copy(fileUploadForm.getFileData().getBytes(), new File(fileUploadForm.getFileData().getOriginalFilename()));
//      
        userService.userProfile_imgUpload(fileUploadForm, principal);
        return "redirect:menu";
    }
   
}
