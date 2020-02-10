/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.service;

import com.attendance.login.user.model.User;
import com.attendance.util.ExceptionDialog;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface LoginService {

    public abstract int saveUser(User user);

    public abstract boolean updateUser(User user);

    public abstract boolean deleteUser(User user);

    public abstract User findById(int userId);

    public abstract User findByUsername(String username);

    public abstract List<User> findAll();

    public abstract List<User> findByDepartment(String department);

    public abstract List<User> findByType(String type);

    public abstract User findByUsernameDepartmentType(String username, String department, String type);

    public abstract List<User> findByStatus(String status);

    public abstract List<User> findByStatusAndDepartment(String status, String department);

    public abstract int count(String sql);

    public abstract boolean login(String username, String password, String type);

    public abstract void setParent(Parent parent);

    public abstract ExceptionDialog getEx();
    
    public abstract boolean isUsernameExists(String username);
}
