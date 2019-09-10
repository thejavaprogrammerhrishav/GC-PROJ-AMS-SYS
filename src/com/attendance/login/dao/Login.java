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

    public abstract List<User> findByName(String userName);

    public abstract List<User> findByEmail(String userEmail);

    public abstract User findByUsername(String username);

    public abstract User findByUsernameAndEmail(String username, String userEmail);

    public abstract List<User> findByAll();

    public abstract List<User> findByType(String type);

    public abstract User findByUsernameAndType(String username, String type);
    
    public abstract int isUsernameExists(String username);
    
    public abstract User findByUsernameTypeEmail(String username,String type,String email);
}
