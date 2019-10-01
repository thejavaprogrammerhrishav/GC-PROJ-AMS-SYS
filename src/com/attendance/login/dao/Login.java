/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.dao;

import com.attendance.login.user.model.User;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface Login {

    public abstract int save(User user);

    public abstract boolean update(User user);

    public abstract boolean delete(User user);

    public abstract User findById(int userId);

    public abstract User findByUsername(String username);

    public abstract List<User> findAll();

    public abstract List<User> findByDepartment(String department);

    public abstract List<User> findByType(String type);

    public abstract User findByUsernameDepartmentType(String username, String department, String type);

    public abstract int isUsernameExists(String username);
    
    public abstract int count(String sql);
    
    public abstract List<User> findByStatus(String status);
    
    public abstract List<User> findByStatusAndDepartment(String status, String department);

}
