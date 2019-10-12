/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.controller;

/**
 *
 * @author pc
 */
public class UTReportModel {
    
    private int slno;
    private int rollno;
    private String name;
    private int total;
    private int passing;
    private int marks;

    public UTReportModel() {
    }

    public UTReportModel(int slno, int rollno, String name, int total, int passing, int marks) {
        this.slno = slno;
        this.rollno = rollno;
        this.name = name;
        this.total = total;
        this.passing = passing;
        this.marks = marks;
    }

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
    
    
}
