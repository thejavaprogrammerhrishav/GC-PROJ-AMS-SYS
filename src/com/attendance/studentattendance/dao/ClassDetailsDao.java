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

    public abstract List<ClassDetails> findByFaculty(String facultyName);

    public abstract List<ClassDetails> findByDate(String date);

    public abstract List<ClassDetails> findByTime(String time);

    public abstract List<ClassDetails> findByDateAndTime(String date, String time);

    public abstract List<ClassDetails> findBySemester(String semester);

    public abstract List<ClassDetails> findByYear(int year);
    
    public abstract List<ClassDetails> findByDepartment(String department);
    
    public abstract List<ClassDetails> findByCourseType(int coursetype);

    public abstract ClassDetails findById(String classId);

    public abstract List<ClassDetails> findBySubjectTaught(String subjectTaught);

    public abstract List<ClassDetails> findBySemesterAndYear(String semester, int year);

    public abstract int countFacultyClasses(String facultyName);

    public abstract int countFacultyClassesDateRange(String facultyName, String dateFrom, String dateTo);

    public abstract int countfacultyClassesSemesterAndYear(String facultyName, String semester, String year);

    public abstract int countFacutlyClassesSemester(String facultyName, String semseter);

    public abstract int countClassesInEntireSemester(String semester, int year);

    public abstract <T> List<T> get(String sql, Class<T> type);

    public abstract int get(String sql);

    public boolean updateClassId(String newId, String oldId);
}
