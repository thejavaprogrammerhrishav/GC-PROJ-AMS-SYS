/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.actions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public abstract class LoginAuthenticator implements LoginListener {

    private final List<LoginSuccessListener> success;
    private final List<LoginFailedListener> failed;
    private final List<LoginStatusListener> status;

    private String accstatus = "";

    public LoginAuthenticator() {
        success = new ArrayList<>();
        failed = new ArrayList<>();
        status = new ArrayList<>();
    }

    protected abstract boolean authenticate(String username, String password);

    public boolean authenticateUser(String username, String password) {
        boolean auth = authenticate(username, password);
        fireLoginStatusListener(accstatus);
        if (auth) {
            fireLoginSuccessListeners();
        } else {
            fireLoginFailedListeners();
        }
        return auth;
    }

    @Override
    public void addLoginFailedListener(LoginFailedListener failedListener) {
        failed.add(failedListener);
    }

    @Override
    public void addLoginSuccessListener(LoginSuccessListener successListener) {
        success.add(successListener);
    }

    @Override
    public void addLoginStatusListener(LoginStatusListener statusListener) {
        status.add(statusListener);
    }

    @Override
    public void fireLoginFailedListeners() {
        failed.stream().forEach(c -> c.loginFailed());
    }

    @Override
    public void fireLoginSuccessListeners() {
        success.stream().forEach(c -> c.loginSuccess());
    }

    @Override
    public void fireLoginStatusListener(String status) {
        this.status.stream().forEach(c -> c.status(status));
    }

    @Override
    public void removeAllLoginFailedListeners() {
        failed.clear();
    }

    @Override
    public void removeAllLoginSuccessListeners() {
        success.clear();
    }

    @Override
    public void removeAllLoginStatusListener() {
        status.clear();
    }

    @Override
    public void removeLoginFailedListener(LoginFailedListener failedListener) {
        if (failed.contains(failedListener)) {
            failed.remove(failedListener);
        }
    }

    @Override
    public void removeLoginSuccessListener(LoginSuccessListener successListener) {
        if (success.contains(successListener)) {
            success.remove(successListener);
        }
    }

    @Override
    public void removeLoginStatusListener(LoginStatusListener statusListener) {
        if (status.contains(statusListener)) {
            status.remove(statusListener);
        }
    }

    public void setAccstatus(String accstatus) {
        this.accstatus = accstatus;
    }
}
