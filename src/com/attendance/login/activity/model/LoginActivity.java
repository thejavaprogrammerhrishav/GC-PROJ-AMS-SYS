/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.activity.model;

import java.io.Serializable;

/**
 *
 * @author Programmer Hrishav
 */
public class LoginActivity implements Serializable{
    private int id;
    private String name;
    private String username;
    private String userType;
    private String status;
    private String logindate;
    private String logintime;
    private String logouttime;

    public LoginActivity() {
    }

    public LoginActivity(String name, String username, String userType, String status, String logindate, String logintime, String logouttime) {
        this.name = name;
        this.username = username;
        this.userType = userType;
        this.status = status;
        this.logindate = logindate;
        this.logintime = logintime;
        this.logouttime = logouttime;
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

}