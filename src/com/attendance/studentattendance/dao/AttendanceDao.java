/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao;

import com.attendance.studentattendance.model.Attendance;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface AttendanceDao {

    public abstract int save(Attendance attendance);

    public abstract boolean update(Attendance attendance);

    public abstract boolean delete(Attendance attendance);

    public abstract List<Attendance> findAll();

    public abstract List<Attendance> findByStatus(String status);

    public abstract List<Attendance> findByClass(String classId);

    public abstract List<Attendance> findByStatusAndClass(String status, String classId);

    public abstract Attendance findById(int id);

    public abstract List<Attendance> findBySemester(String semester);

    public abstract List<Attendance> findByYear(int year);

    public abstract List<Attendance> findBySemesterAndYear(String semester, String year);

    public abstract List<Attendance> findByStudentId(String studentId);

    public abstract <T> List<T> get(String sql, Class<T> type);
    
    public abstract <T> List<T> getCustom(String sql, Class<T> type);

}
