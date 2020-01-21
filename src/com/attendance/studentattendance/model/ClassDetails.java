/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.model;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Programmer Hrishav
 */
public class ClassDetails {
    
    @NotNull(message="{classid.null}")
    @NotEmpty(message="{classid.empty}")
    private String classId;
    
    @NotNull(message="{classid.facultyName.null}")
    @NotEmpty(message="{classid.facultyName.empty}")
    private String facultyName;
    
    @NotNull(message="{classid.subjectTaught.null}")
    @NotEmpty(message="{classid.subjectTaught.empty}")
    private String subjectTaught;
    
    @NotNull(message="{classid.date.null}")
    @NotEmpty(message="{classid.date.empty}")
    private String date;
    
    @NotNull(message="{classid.time.null}")
    @NotEmpty(message="{classid.time.empty}")
    private String time;
    
    @NotNull(message="{classid.semester.null}")
    @NotEmpty(message="{classid.semester.empty}")
    private String semester;
    
    @NotNull(message="{classid.year.null}")
    @NotEmpty(message="{classid.year.empty}")
    private int year;
    
    @NotNull(message="{classid.paper.null}")
    @NotEmpty(message="{classid.paper.empty}")
    private String paper;
    
    @NotNull(message="{classid.acadamicyear.null}")
    @NotEmpty(message="{classid.acadamicyear.empty}")
    private String acadamicyear;
    
    @NotNull(message="{classid.department.null}")
    @NotEmpty(message="{classid.department.empty}")
    private String department;
    
    @NotNull(message="{classid.coursetype.null}")
    @NotEmpty(message="{classid.coursetype.empty}")
    private String coursetype;
    
    @Valid
    private DailyStats dailyStats;
    
    private List<Attendance> attendance;

    public ClassDetails() {
    }

    public ClassDetails(String classId, String facultyName, String subjectTaught, String date, String time, String semester, int year, String paper, String acadamicyear, String department, String coursetype) {
        this.classId = classId;
        this.facultyName = facultyName;
        this.subjectTaught = subjectTaught;
        this.date = date;
        this.time = time;
        this.semester = semester;
        this.year = year;
        this.paper = paper;
        this.acadamicyear = acadamicyear;
        this.department = department;
        this.coursetype = coursetype;
    }
    
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSubjectTaught() {
        return subjectTaught;
    }

    public void setSubjectTaught(String subjectTaught) {
        this.subjectTaught = subjectTaught;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
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

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public DailyStats getDailyStats() {
        return dailyStats;
    }

    public void setDailyStats(DailyStats dailyStats) {
        this.dailyStats = dailyStats;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }
   
    
}
