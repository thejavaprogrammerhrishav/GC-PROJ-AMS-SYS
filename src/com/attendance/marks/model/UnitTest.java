/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Programmer Hrishav
 */
public class UnitTest {

    private int id;

    @NotEmpty(message = "{attendance.name}")
    private String name;

    @Min(value = 1000, message = "{attendance.roll.number}")
    private int rollno;

    @Min(value = 0, message = "{attendance.marks}")
    private int marksObtained;

    @Min(value = 0, message = "{attendance.marks}")
    private int passingMarks;

    @Min(value = 0, message = "{attendance.marks}")
    private int totalMarks;

    @NotEmpty(message = "{attendance.empty}")
    private String unitTest;

    @NotEmpty(message = "{attendance.acadamic.year}")
    private String acadamicYear;

    @NotEmpty(message = "{attendance.semester}")
    private String semester;

    @Range(min = 1800, max = 3000, message = "{attendance.year}")
    private int year;

    @NotEmpty(message = "{attendance.course.type}")
    private String coursetype;

    @NotEmpty(message = "{attendance.department}")
    private String department;

    public UnitTest() {
        this(-1, "", -1, 0, 0, 0, "", "", "", -1, "", "");
    }

    public UnitTest(int id, String name, int rollno, int marksObtained, int passingMarks, int totalMarks, String unitTest, String acadamicYear, String semester, int year, String coursetype, String department) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.marksObtained = marksObtained;
        this.passingMarks = passingMarks;
        this.totalMarks = totalMarks;
        this.unitTest = unitTest;
        this.acadamicYear = acadamicYear;
        this.semester = semester;
        this.year = year;
        this.coursetype = coursetype;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public int getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(int passingMarks) {
        this.passingMarks = passingMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getUnitTest() {
        return unitTest;
    }

    public void setUnitTest(String unitTest) {
        this.unitTest = unitTest;
    }

    public String getAcadamicYear() {
        return acadamicYear;
    }

    public void setAcadamicYear(String acadamicYear) {
        this.acadamicYear = acadamicYear;
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
