/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.user.principal.model;

/**
 *
 * @author Programmer Hrishav
 */
public class Principal {
    private String name;
    private String emailId;
    private String contact;
    private String gender;

    public Principal() {
    }

    public Principal(String name, String emailId, String contact, String gender) {
        this.name = name;
        this.emailId = emailId;
        this.contact = contact;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
}
