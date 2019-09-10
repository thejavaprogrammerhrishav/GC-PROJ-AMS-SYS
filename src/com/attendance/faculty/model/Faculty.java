/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.model;

/**
 *
 * @author Programmer Hrishav
 */
public class Faculty {
    private String firstName;
    private String lastName;
    private String emailId;
    private String contact;
    private String gender;
    private String department;

    public Faculty() {
        this("","","","","","");
    }

    public Faculty(String firstName, String lastName, String emailId, String contact, String gender, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.contact = contact;
        this.gender = gender;
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
    
    public String getFullName(){
        return getFirstName()+" "+getLastName();
    }
}
