/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.papers.model;

/**
 *
 * @author Programmer Hrishav
 */
public class Paper {
    private String paperCode;
    private String paperName;
    private String semester;
    private String department;
    private String coursetype;

    public Paper() {
        this("","","","","");
    }

    public Paper(String paperCode, String paperName, String semester, String department, String coursetype) {
        this.paperCode = paperCode;
        this.paperName = paperName;
        this.semester = semester;
        this.department = department;
        this.coursetype = coursetype;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
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
       
}
