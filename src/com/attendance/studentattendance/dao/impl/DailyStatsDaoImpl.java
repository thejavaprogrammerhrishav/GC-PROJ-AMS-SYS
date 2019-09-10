/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao.impl;

import com.attendance.studentattendance.dao.DailyStatsDao;
import com.attendance.studentattendance.model.DailyStats;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class DailyStatsDaoImpl implements DailyStatsDao {

    private HibernateTemplate hibernateTemplate;
    private JdbcTemplate jdbcTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int save(DailyStats daily) {
        return (Integer) hibernateTemplate.save(daily);
    }

    @Override
    @Transactional
    public boolean update(DailyStats daily) {
        hibernateTemplate.update(daily);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(DailyStats daily) {
        hibernateTemplate.delete(daily);
        return true;
    }

    @Override
    @Transactional
    public List<DailyStats> findAll() {
        return hibernateTemplate.loadAll(DailyStats.class);
    }

    @Override
    @Transactional
    public DailyStats findById(int id) {
        return hibernateTemplate.get(DailyStats.class, id);
    }

    @Override
    public List<DailyStats> findByTotalStudents(int total) {
        return jdbcTemplate.queryForList("select * from dailystats where totalstudents>=" + total, DailyStats.class);
    }

    @Override
    public List<DailyStats> findByTotalStudentsPresent(int total) {
        return jdbcTemplate.queryForList("select * from dailystats where totalpresent>=" + total, DailyStats.class);
    }

    @Override
    public List<DailyStats> findByTotalStudentsAbsent(int total) {
        return jdbcTemplate.queryForList("select * from dailystats where totalabsent>=" + total, DailyStats.class);
    }

    @Override
    public List<DailyStats> findByPresentPercentage(double percentage) {
        return jdbcTemplate.queryForList("select * from dailystats where presentpercentage>=" + percentage, DailyStats.class);
    }

    @Override
    public List<DailyStats> findByAbsentPercentage(double percentage) {
        return jdbcTemplate.queryForList("select * from dailystats where absentpercentage>=" + percentage, DailyStats.class);
    }

    @Override
    @Transactional
    public List<DailyStats> findByClassId(String classId) {
        return (List<DailyStats>) hibernateTemplate.find("from DailyStats where classid=?", "%" + classId + "%");
    }

    @Override
    public <T> List<T> get(String sql, Class<T> type) {
        return jdbcTemplate.queryForList(sql, type);
    }

}
