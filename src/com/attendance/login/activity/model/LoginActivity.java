/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.activity.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Programmer Hrishav
 */
public class LoginActivity implements Serializable {

    private int id;

    @NotEmpty(message = "{attendance.name}")
    private String name;

    @NotEmpty(message = "{attendance.username}")
    private String username;

    @NotEmpty(message = "{attendance.user.type}")
    private String userType;

    @NotEmpty(message = "{attendance.status}")
    private String status;

    @NotEmpty(message = "{attendance.date}")
    private String logindate;

    @NotEmpty(message = "{attendance.time}")
    private String logintime;

    @NotNull(message = "{attendance.null}")
    private String logouttime;

    @NotEmpty(message = "{attendance.department}")
    private String department;

    public LoginActivity() {
    }

    public LoginActivity(String name, String username, String userType, String status, String logindate, String logintime, String logouttime, String department) {
        this.name = name;
        this.username = username;
        this.userType = userType;
        this.status = status;
        this.logindate = logindate;
        this.logintime = logintime;
        this.logouttime = logouttime;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogindate() {
        return logindate;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getLogouttime() {
        return logouttime;
    }

    public void setLogouttime(String logouttime) {
        this.logouttime = logouttime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
