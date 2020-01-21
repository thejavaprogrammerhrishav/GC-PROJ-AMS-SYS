/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.model;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


/**
 *
 * @author Programmer Hrishav
 */
public class ClassDetails {
    
    @NotEmpty(message="{attendance.class.id}")
    private String classId;
    
    @NotEmpty(message="{attendance.name}")
    private String facultyName;
    
    @NotEmpty(message="{attendance.subject.taught}")
    private String subjectTaught;
    
    @NotEmpty(message="{attendance.date}")
    private String date;
    
    @NotEmpty(message="{attendance.time}")
    private String time;
    
    @NotEmpty(message="{attendance.semester}")
    private String semester;
    
    @Range(min=1800, max=3000,message = "{attendance.year}")
    private int year;
    
    @NotEmpty(message="{attendance.paper.code}")
    private String paper;
    
    @NotEmpty(message="{attendance.acadamic.year}")
    private String acadamicyear;
    
    @NotEmpty(message="{attendance.department}")
    private String department;
    
    @NotEmpty(message="{attendance.course.type}")
    private String coursetype;
    
    @NotNull(message = "{attendance.null}")
    @Valid
    private DailyStats dailyStats;
    
    @NotNull(message = "{attendance.empty}")
    @Valid
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
