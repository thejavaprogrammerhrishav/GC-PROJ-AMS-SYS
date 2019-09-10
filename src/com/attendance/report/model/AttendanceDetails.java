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
public class AttendanceDetails {
    private String studentname;
    private int rollno;
    private String semester;
    private int year;
    private String facultyname;
    private int totalclasses;
    private int totalpresent;
    private int totalabsent;
    private double presentpercentage;
    private double absentpercentage;

    public AttendanceDetails() {
        this("",-1,"",-1,"",-1,-1,-1,-1.0,-1.0);
    }

    public AttendanceDetails(String studentname, Integer rollno, String semester, int year, String facultyname, int totalclasses, int totalpresent, int totalabsent, double presentpercentage, double absentpercentage) {
        this.studentname = studentname;
        this.rollno = rollno;
        this.semester = semester;
        this.year = year;
        this.facultyname = facultyname;
        this.totalclasses = totalclasses;
        this.totalpresent = totalpresent;
        this.totalabsent = totalabsent;
        this.presentpercentage = presentpercentage;
        this.absentpercentage = absentpercentage;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public int getTotalclasses() {
        return totalclasses;
    }

    public void setTotalclasses(int totalclasses) {
        this.totalclasses = totalclasses;
    }

    public int getTotalpresent() {
        return totalpresent;
    }

    public void setTotalpresent(int totalpresent) {
        this.totalpresent = totalpresent;
    }

    public int getTotalabsent() {
        return totalabsent;
    }

    public void setTotalabsent(int totalabsent) {
        this.totalabsent = totalabsent;
    }

    public double getPresentpercentage() {
        return presentpercentage;
    }

    public void setPresentpercentage(double presentpercentage) {
        this.presentpercentage = presentpercentage;
    }

    public double getAbsentpercentage() {
        return absentpercentage;
    }

    public void setAbsentpercentage(double absentpercentage) {
        this.absentpercentage = absentpercentage;
    }

        
}
