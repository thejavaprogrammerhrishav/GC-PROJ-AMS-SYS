/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.model;

/**
 *
 * @author pc
 */
public class Routine {
    
    
    private int id;
    private String name;
    private String usertype;
    private String date;
    private String department;
    private byte[] image;

    public Routine() {
    }

    public Routine(int id, String name, String usertype, String date, String department, byte[] image) {
        this.id = id;
        this.name = name;
        this.usertype = usertype;
        this.date = date;
        this.department = department;
        this.image = image;
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

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
}
