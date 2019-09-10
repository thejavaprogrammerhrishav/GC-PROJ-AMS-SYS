/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.model;

/**
 *
 * @author Programmer Hrishav
 */
public class DailyStats {

    private int id;
    private String classId;
    private int totalStudent;
    private int totalPresent;
    private int totalAbsent;
    private double presentPercentage;
    private double absentPercentage;

    public DailyStats() {
        this(-1, "", -1, -1, -1, -1.1, -1.1);
    }

    public DailyStats(int id, String classId, int totalStudent, int totalPresent, int totalAbsent, double presentPercentage, double absentPercentage) {
        this.id = id;
        this.classId = classId;
        this.totalStudent = totalStudent;
        this.totalPresent = totalPresent;
        this.totalAbsent = totalAbsent;
        this.presentPercentage = presentPercentage;
        this.absentPercentage = absentPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

}
