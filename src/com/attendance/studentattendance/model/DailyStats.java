/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.model;

import com.attendance.validator.annotation.Percentage;
import javax.validation.constraints.Min;

/**
 *
 * @author Programmer Hrishav
 */
public class DailyStats {

    
    private Long id;
    
    @Min(value=0,message = "{attendance.total.student}")
    private int totalStudent;
        
    @Min(value=0,message = "{attendance.total.student}")
    private int totalPresent;
        
    @Min(value=0,message = "{attendance.total.student}")
    private int totalAbsent;

    @Percentage(message="{attendance.percentage}")
    private double presentPercentage;
    
    @Percentage(message="{attendance.percentage}")
    private double absentPercentage;

    public DailyStats() {
        this((long) -1, -1, -1, -1, -1.1, -1.1);
    }

    public DailyStats(Long id, int totalStudent, int totalPresent, int totalAbsent, double presentPercentage, double absentPercentage) {
        this.id = id;
        this.totalStudent = totalStudent;
        this.totalPresent = totalPresent;
        this.totalAbsent = totalAbsent;
        this.presentPercentage = presentPercentage;
        this.absentPercentage = absentPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
