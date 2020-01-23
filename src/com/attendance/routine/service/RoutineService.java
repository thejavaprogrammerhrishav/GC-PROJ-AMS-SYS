/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routine.service;

import com.attendance.routines.model.Routine;
import java.util.List;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface RoutineService {

    public abstract Integer saveRoutine(Routine R);

    public abstract boolean updateRoutine(Routine R);

    public abstract boolean deleteRoutine(Routine R);

    public abstract List<Routine> findAll();

    public abstract Routine findById(Integer id);

    public abstract List<Routine> findAllepartment(String department);

    public abstract List<Routine> findByDate(String date);

    public abstract List<Routine> findBydepartmentAndDate(String department, String date);

    public abstract List<Routine> findByDepartmentAndYear(String department, String year);

    public abstract List<Routine> sortByDepartment(String department, String order);

    public abstract Routine findByDepartmentAndDateAndStatus(String department, String date, String status);

    public abstract List<Routine> findByDepartmentAndStatus(String department, String status);

    public abstract Integer hasActiveRoutine(String department, String year);

    public abstract <T> List<T> get(String sql, Class<T> type);
}
