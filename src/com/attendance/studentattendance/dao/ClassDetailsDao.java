/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao;

import com.attendance.studentattendance.model.ClassDetails;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface ClassDetailsDao {

    public abstract String save(ClassDetails details);

    public abstract boolean update(ClassDetails details);

    public abstract boolean delete(ClassDetails details);

    public abstract List<ClassDetails> findAll();

    public abstract ClassDetails findById(String id);

    public abstract List<ClassDetails> findByFacultyName(String facultyname);

    public abstract List<ClassDetails> findByDate(String date);

    public abstract List<ClassDetails> findByTime(String time);

    public abstract List<ClassDetails> findBySemester(String semester);

    public abstract List<ClassDetails> findByAcadamicYear(String acadamicYear);

    public abstract List<ClassDetails> findByYear(int year);

    public abstract List<ClassDetails> findBySemesterAndYear(String semester, int year);

    public abstract List<ClassDetails> findByAcadamicYearAndYear(String acadamicYear, int year);

    public abstract List<ClassDetails> findByDepartmentAndSemesterAndPaperCodeAndYear(String department, String semester, String papercode, int year);

    public abstract List<ClassDetails> findByDepartment(String department);

    public abstract List<ClassDetails> findByDepartmentAndSemesterAndYear(String department, String semester, int year);

    public abstract List<ClassDetails> findByDepartmentAndAcadamicYearAndYear(String department, String acadamicYear, int year);

    public abstract List<ClassDetails> findByDepartmentAndCourseType(String department, String courseType);

    public abstract List<ClassDetails> findByDepartmentAndCourseTypeAndYear(String department, String courseType, int year);

    public abstract List<ClassDetails> findByDepartmentAndCourseTypeAndAcadamicYearAndYear(String department, String courseType, String acadamicYear, int year);

    public abstract List<ClassDetails> findByDepartmentAndCourseTypeAndSemesterAndYear(String department, String courseType, String semester, int year);

    public abstract List<ClassDetails> findByFacultyAndDepartmentAndCourseTypeAndSemesterAndYear(String faculty, String department, String courseType, String semester, int year);

    public abstract List<ClassDetails> findByFacultyAndDepartmentAndCourseTypeAndAcadamicAndYear(String faculty, String department, String courseType, String acadamicYear, int year);

    public abstract List<ClassDetails> findAll(String department,String acadamicYear,String semester,int year,String papercode,String coursetype);

    public abstract boolean updateClassId(String oldId,String newId);
}
