/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author pc
 */
public class Routine {

    private int id;

    @NotEmpty(message = "{attendance.name}")
    private String name;

    @NotEmpty(message = "{attendance.user.type}")
    private String usertype;

    @NotEmpty(message = "{attendance.date")
    private String date;

    @NotEmpty(message = "{attendance.department}")
    private String department;

    @NotNull(message = "{attendance.null}")
    private byte[] image;

    @NotEmpty(message = "{attendance.name}")
    private String filename;

    @NotEmpty(message = "{attendance.status}")
    private String status;

    public Routine() {
    }

    public Routine(int id, String name, String usertype, String date, String department, byte[] image, String filename, String status) {
        this.id = id;
        this.name = name;
        this.usertype = usertype;
        this.date = date;
        this.department = department;
        this.image = image;
        this.filename = filename;
        this.status = status;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
