/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Programmer Hrishav
 */
public class Notes {

    private int id;
    
    @NotEmpty(message = "{attendance.name}")
    private String facultyName;

    @NotEmpty(message = "{attendance.name}")
    private String fileName;

    private double fileSize;

    @NotEmpty(message = "{attendance.date}")
    private String uploadDate;

    @NotEmpty(message = "{attendance.null}")
    private byte[] file;

    @NotEmpty(message = "{attendance.department}")
    private String department;

    public Notes() {
        this(-1, "", "", -1, "", null, "");
    }

    public Notes(int id, String facultyName, String fileName, double fileSize, String uploadDate, byte[] file, String department) {
        this.id = id;
        this.facultyName = facultyName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.file = file;
        this.department = department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
