/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao;

import com.attendance.studentattendance.model.DailyStats;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface DailyStatsDao {

    public abstract int save(DailyStats daily);

    public abstract boolean update(DailyStats daily);

    public abstract boolean delete(DailyStats daily);

    public abstract List<DailyStats> findAll();

    public abstract DailyStats findById(int id);

    public abstract List<DailyStats> findByTotalStudents(int total);

    public abstract List<DailyStats> findByTotalStudentsPresent(int total);

    public abstract List<DailyStats> findByTotalStudentsAbsent(int total);

    public abstract List<DailyStats> findByPresentPercentage(double percentage);

    public abstract List<DailyStats> findByAbsentPercentage(double percentage);

    public abstract List<DailyStats> findByClassId(String classId);

    public abstract <T> List<T> get(String sql, Class<T> type);
}
