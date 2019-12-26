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
@FunctionalInterface
public interface LoginStatusListener {
    public abstract void status(String status);
}
