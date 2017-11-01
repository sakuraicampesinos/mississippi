/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.msp_test1.app.model;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author saku
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {

    // ログイン中のユーザidを取得する。そのidを引数にして全てタスクを抽出
    @Query("select t from Task t where user_id = :u_id ")
    public List<Task> findByMyTask(@Param("u_id") int u_id);

    // 今日のタスクを取得
    @Query("select t from Task t where user_id = :u_id AND create_date > current_date")
    public List<Task> findByTodayTask(@Param("u_id") int u_id);
//    
    // 直近のタスクを取得

    @Query("select t "
            + "from Task t "
            + "where create_date = ("
            + "select MAX(create_date) "
            + "from Task t "
            + "where user_id = :u_id)"
    )
    public Task findByNowTask(@Param("u_id") int u_id);

//    @Query("SELECT * FROM tasks AS m WHERE create_date = (SELECT MAX(create_date) FROM tasks AS s WHERE m.user_id = s.user_id AND NOT (user_id = :u_id)", nativeQuery = true)
    @Query(value = "SELECT * FROM tasks AS m WHERE create_date = (SELECT MAX(create_date) FROM tasks AS s WHERE m.user_id = s.user_id AND NOT (user_id = :u_id))", nativeQuery = true)
    public List<Task> findByOtherTask(@Param("u_id") int u_id);

}
