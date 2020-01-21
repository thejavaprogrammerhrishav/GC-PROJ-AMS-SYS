/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.personal.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Programmer Hrishav
 */
public class PersonalDetails {

    private int id;

    @NotEmpty(message = "{attendance.name}")
    private String name;

    @NotEmpty(message = "{attendance.email}")
    @Size(min=1,max=100,message = "{attendance.email}")
    @Email(message = "{attendance.email}")
    private String emailId;

    @NotEmpty(message = "{attendance.contact}")
    @Size(min = 10, max = 10,message = "{attendance.contact}")
    @Pattern(regexp = "^[6-9]\\d{9}$",message = "{attendance.contact}")
    private String contact;

    @NotEmpty(message = "{attendance.gender}")
    private String gender;

    public PersonalDetails() {
    }

    public PersonalDetails(int id, String name, String emailId, String contact, String gender) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.contact = contact;
        this.gender = gender;
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
