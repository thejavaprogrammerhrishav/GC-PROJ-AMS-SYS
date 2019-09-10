/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.model;

import java.io.Serializable;

/**
 *
 * @author Programmer Hrishav
 */
public class Student implements Serializable {

    private String id;
    private String name;
    private int rollno;
    private int year;
    private String gender;
    private String contact;
    private String acadamicyear;
    private String courseType;
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
        this.department =department;
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
