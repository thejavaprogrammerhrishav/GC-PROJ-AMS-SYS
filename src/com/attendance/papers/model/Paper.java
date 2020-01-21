  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.papers.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Programmer Hrishav
 */
public class Paper {
    private long id;
    
    @NotEmpty(message = "{attendance.paper.code}")
    private String paperCode;
    
    @NotEmpty(message = "{attendance.name}")
    private String paperName;
    
    @NotEmpty(message = "{attendance.semester}")
    private String semester;
    
    @NotEmpty(message = "{attendance.department}")
    private String department;
    
    @NotEmpty(message = "{attendance.course.type}")
    private String coursetype;

    public Paper() {
    }

    public Paper(long id, String paperCode, String paperName, String semester, String department, String coursetype) {
        this.id = id;
        this.paperCode = paperCode;
        this.paperName = paperName;
        this.semester = semester;
        this.department = department;
        this.coursetype = coursetype;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }

    
       
}
