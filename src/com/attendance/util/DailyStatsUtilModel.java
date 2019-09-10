/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

/**
 *
 * @author Programmer Hrishav
 */
public class DailyStatsUtilModel {
    private String date;
    private String time;
    private String semester;
    private String year;
    private int totalStudent;
    private int totalPresent;
    private int totalAbsent;
    private double presentPercentage;
    private double absentPercentage;
    private String acadamicyear;
    private String coursetype;

    public DailyStatsUtilModel() {
        this("","","","",0,0,0,0.0,0.0,"","");
    }

    public DailyStatsUtilModel(String date, String time, String semester, String year, int totalStudent, int totalPresent, int totalAbsent, double presentPercentage, double absentPercentage, String acadamicyear, String coursetype) {
        this.date = date;
        this.time = time;
        this.semester = semester;
        this.year = year;
        this.totalStudent = totalStudent;
        this.totalPresent = totalPresent;
        this.totalAbsent = totalAbsent;
        this.presentPercentage = presentPercentage;
        this.absentPercentage = absentPercentage;
        this.acadamicyear = acadamicyear;
        this.coursetype = coursetype;
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

    public int getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(int totalStudent) {
        this.totalStudent = totalStudent;
    }

    public int getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(int totalPresent) {
        this.totalPresent = totalPresent;
    }

    public int getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(int totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public double getPresentPercentage() {
        return presentPercentage;
    }

    public void setPresentPercentage(double presentPercentage) {
        this.presentPercentage = presentPercentage;
    }

    public double getAbsentPercentage() {
        return absentPercentage;
    }

    public void setAbsentPercentage(double absentPercentage) {
        this.absentPercentage = absentPercentage;
    }

    public String getAcadamicyear() {
        return acadamicyear;
    }

    public void setAcadamicyear(String acadamicyear) {
        this.acadamicyear = acadamicyear;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }

    public String getCoursetype() {
        return coursetype;
    }
        
}
