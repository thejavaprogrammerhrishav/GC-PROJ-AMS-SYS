/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.user.model;

import com.attendance.personal.model.PersonalDetails;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author Programmer Hrishav
 */
public class User implements Serializable {

    private int id;

    @NotEmpty(message = "{attendance.username}")
    private String username;

    @NotEmpty(message = "{attendance.password}")
    @Size(min = 8, max = 25, message = "attendance.password")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,25})", message = "{attendance.password.pattern}")
    private String password;

    @NotEmpty(message = "{attendance.user.type}")
    private String type;

    @NotEmpty(message = "{attendance.department}")
    private String department;

    @NotEmpty(message = "{attendance.date}")
    private String date;

    @NotEmpty(message = "{attendance.status}")
    private String status;

    @NotNull(message = "{attendance.null}")
    private byte[] image;

    @NotNull(message = "{attendance.null}")
    @Valid
    private PersonalDetails details;

    @NotNull(message = "{attendance.null}")
    @Valid
    private SecurityQuestion securityquestion;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public PersonalDetails getDetails() {
        return details;
    }

    public void setDetails(PersonalDetails details) {
        this.details = details;
    }

    public SecurityQuestion getSecurityquestion() {
        return securityquestion;
    }

    public void setSecurityquestion(SecurityQuestion securityquestion) {
        this.securityquestion = securityquestion;
    }

    public boolean hasSecurityQuestion() {
        return !(getSecurityquestion().getAnswer1().isEmpty() || getSecurityquestion().getAnswer2().isEmpty() || getSecurityquestion().getAnswer3().isEmpty());
    }

}
