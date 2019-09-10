/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.actions;

/**
 *
 * @author Programmer Hrishav
 */
public interface LoginListener {

    public abstract void addLoginSuccessListener(LoginSuccessListener successListener);

    public abstract void addLoginFailedListener(LoginFailedListener failedListener);

    public abstract void removeLoginSuccessListener(LoginSuccessListener successListener);

    public abstract void removeLoginFailedListener(LoginFailedListener failedListener);

    public abstract void fireLoginSuccessListeners();

    public abstract void fireLoginFailedListeners();

    public abstract void removeAllLoginSuccessListeners();

    public abstract void removeAllLoginFailedListeners();
}
