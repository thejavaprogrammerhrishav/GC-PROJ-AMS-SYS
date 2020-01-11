/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.student.model.Student;

/**
 *
 * @author Programmer Hrishav
 */
public class AttendanceUtilModel {
    private String date;
    private String time;
    private String semester;
    private String year;
    private String studentId;
    private String status;
    private String acadamicyear;
    private String coursetype;
    private int roll;
    private String name;

    public AttendanceUtilModel() {
    }

    public AttendanceUtilModel(String date, String time, String semester, String year, String studentId, String status, String acadamicyear, String coursetype, int roll, String name) {
        this.date = date;
        this.time = time;
        this.semester = semester;
        this.year = year;
        this.studentId = studentId;
        this.status = status;
        this.acadamicyear = acadamicyear;
        this.coursetype = coursetype;
        this.roll = roll;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }  

    public void setAcadamicyear(String acadamicyear) {
        this.acadamicyear = acadamicyear;
    }

    public String getAcadamicyear() {
        return acadamicyear;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getRoll() {
        return roll;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
}
