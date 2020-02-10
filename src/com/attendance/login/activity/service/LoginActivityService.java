/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.activity.service;

import com.attendance.login.activity.model.LoginActivity;
import com.attendance.util.ExceptionDialog;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public interface LoginActivityService {
    public abstract int saveactivity(LoginActivity login);

    public abstract int updateactivity(LoginActivity login);

    public abstract LoginActivity findByUsername(String username);

    public abstract List<LoginActivity> findByDate(String date);
    
    public abstract List<LoginActivity> findByName(String name);
    
    public abstract List<LoginActivity> findByDepartment(String department);

    public abstract List<LoginActivity> findByStatus(String status);
    
    public abstract List<LoginActivity> findByUserType(String type);

    public abstract List<LoginActivity> findAll();
    
    public abstract List<LoginActivity> get(String sql);
    
    public abstract void setParent(Parent parent);
    
    public abstract ExceptionDialog getEx();
}
