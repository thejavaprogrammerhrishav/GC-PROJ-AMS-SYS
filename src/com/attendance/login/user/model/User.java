/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.user.model;

import java.io.Serializable;

/**
 *
 * @author Programmer Hrishav
 */
public class User implements Serializable{
    private int id;
    private int personalid;
    private String username;
    private String password;
    private String type;
    private String department;
    private String date;
    private String status;
    private byte[] image;

    public User() {
    }

    public User(int id, int personalid, String username, String password, String type, String department, String date, String status, byte[] image) {
        this.id = id;
        this.personalid = personalid;
        this.username = username;
        this.password = password;
        this.type = type;
        this.department = department;
        this.date = date;
        this.status = status;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonalid() {
        return personalid;
    }

    public void setPersonalid(int personalid) {
        this.personalid = personalid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    
}