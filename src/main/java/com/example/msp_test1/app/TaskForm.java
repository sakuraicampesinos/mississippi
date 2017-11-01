 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author saku
 */
public class TaskForm implements Serializable{
    
    private static final long serialVersionUID = 1L;

    
    @NotBlank(message="タイトルを入力してください")
    private String title;

    @NotBlank(message="タスク内容を入力してください")
    private String content;
    
    private Date create_date;
    
    @NotBlank(message="開始時間を入力してください")
    private String start_date;
    
    @NotBlank(message="終了時間を入力してください")
    private String end_date;
    
    private String remarks;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
    
}
