/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Programmer Hrishav
 */
public class Utils {

    public static Utils util = new Utils();

    private final Login login;

    public Utils() {
        login = (Login) Start.app.getBean("userlogin");
    }

    public List<User> getHODUsers() {
        return login.findByType("HOD");
    }

    public List<User> getPrincipalUsers() {
        return login.findByType("Principal");
    }

    public List<User> getFacultyUsers() {
        return login.findByType("Faculty");
    }

    public List<User> getHODUsers(String department) {
        return login.findByType("HOD").stream().filter(l -> l.getDepartment().equals(department)).collect(Collectors.toList());
    }

    public List<User> getFacultyUsers(String department) {
        return login.findByType("Faculty").stream().filter(l -> l.getDepartment().equals(department)).collect(Collectors.toList());
    }
}
