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
    private String contact;
    private String username;
    private String password;
    private String type;
    private byte[] image;

    public User() {
    }

    public User(int id, String contact, String username, String password, String type,byte[] image) {
        this.id = id;
        this.contact = contact;
        this.username = username;
        this.password = password;
        this.type = type;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return super.toString(); 
    }   
}
