/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.report.model;

/**
 *
 * @author Programmer Hrishav
 */
public class StudentCount {
    private String studentid;
    private int countpresent;

    public StudentCount() {
    }

    public StudentCount(String studentid, int countpresent) {
        this.studentid = studentid;
        this.countpresent = countpresent;
    }

    public int getCountpresent() {
        return countpresent;
    }

    public void setCountpresent(int countpresent) {
        this.countpresent = countpresent;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

}
