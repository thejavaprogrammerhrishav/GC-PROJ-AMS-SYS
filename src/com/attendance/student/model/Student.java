/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.model;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Programmer Hrishav
 */
public class Student implements Serializable {

    @NotEmpty(message = "{attendance.student.id}")
    private String id;

    @NotEmpty(message = "{attendance.name}")
    private String name;

    @Min(value = 1000,message = "{attendance.roll.number}")
    private int rollno;

    @Range(min = 1800, max = 3000, message = "{attendance.year}")
    private int year;

    @NotEmpty(message = "{attendance.gender}")
    private String gender;

    @NotEmpty(message = "{attendance.contact}")
    @Size(min = 10, max = 10, message = "{attendance.contact}")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "{attendance.contact}")
    private String contact;

    @NotEmpty(message = "{attendance.acadamic.year}")
    private String acadamicyear;

    @NotEmpty(message = "{attendance.course.type}")
    private String courseType;

    @NotEmpty(message = "{attendance.department}")
    private String department;

    public Student() {
    }

    public Student(String id, String name, int rollno, int year, String gender, String contact, String acadamicyear, String courseType, String department) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.year = year;
        this.gender = gender;
        this.contact = contact;
        this.acadamicyear = acadamicyear;
        this.courseType = courseType;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAcadamicyear() {
        return acadamicyear;
    }

    public void setAcadamicyear(String acadamicyear) {
        this.acadamicyear = acadamicyear;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return getName() + "==" + getRollno() + "==" + getId();
    }
}
