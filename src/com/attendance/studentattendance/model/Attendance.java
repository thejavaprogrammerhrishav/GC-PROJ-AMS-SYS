/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Programmer Hrishav
 */
public class Attendance {

    private Long id;
    
    @NotEmpty(message="{attendance.student.id}")
    private String studentId;
    
    @NotEmpty(message = "{attendance.status}")
    private String status;

    public Attendance() {
        this((long)-1, "", "", "");
    }

    public Attendance(Long id, String studentId, String status, String classId) {
        this.id = id;
        this.studentId = studentId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
