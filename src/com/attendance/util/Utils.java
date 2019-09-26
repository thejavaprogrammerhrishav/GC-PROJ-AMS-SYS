/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Programmer Hrishav
 */
public class Utils {

    public static Utils util = new Utils();

    private final Login login;
    private final PersonalDetailsDao pdao;

    public Utils() {
        login = (Login) Start.app.getBean("userlogin");
        pdao = (PersonalDetailsDao) Start.app.getBean("personal");
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

    public List<PersonalDetails> getHODDetails() {
        return login.findByType("HOD").stream().map(f -> pdao.findById(f.getPersonalid())).collect(Collectors.toList());
    }

    public List<PersonalDetails> getFacultyDetails() {
        return login.findByType("Faculty").stream().map(f -> pdao.findById(f.getPersonalid())).collect(Collectors.toList());
    }

    public List<PersonalDetails> getPrincipalDetails() {
        return login.findByType("Principal").stream().map(f -> pdao.findById(f.getPersonalid())).collect(Collectors.toList());
    }

    public List<PersonalDetails> getHODDetails(String department) {
        return login.findByType("HOD").stream().filter(p -> p.getDepartment().equals(department)).map(f -> pdao.findById(f.getPersonalid())).collect(Collectors.toList());
    }

    public List<PersonalDetails> getFacultyDetails(String department) {
        return login.findByType("Faculty").stream().filter(p -> p.getDepartment().equals(department)).map(f -> pdao.findById(f.getPersonalid())).collect(Collectors.toList());
    }

    public List<PersonalDetails> getDetails(String department) {
        return login.findByDepartment(department).stream().map(f -> pdao.findById(f.getPersonalid())).collect(Collectors.toList());
    }
}
