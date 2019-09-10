/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao.impl;

import com.attendance.studentattendance.dao.AttendanceDao;
import com.attendance.studentattendance.model.Attendance;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class AttendanceDaoImpl implements AttendanceDao {

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
    public int save(Attendance attendance) {
        return (Integer) hibernateTemplate.save(attendance);
    }

    @Override
    @Transactional
    public boolean update(Attendance attendance) {
        hibernateTemplate.update(attendance);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Attendance attendance) {
        hibernateTemplate.delete(attendance);
        return true;
    }

    @Override
    @Transactional
    public List<Attendance> findAll() {
        return hibernateTemplate.loadAll(Attendance.class);
    }

    @Override
    @Transactional
    public List<Attendance> findByStatus(String status) {
        return (List<Attendance>) hibernateTemplate.find("from Attendance where status=?", status);
    }

    @Override
    @Transactional
    public List<Attendance> findByClass(String classId) {
        return (List<Attendance>) hibernateTemplate.find("from Attendance where classId=?", classId);

    }

    @Override
    @Transactional
    public List<Attendance> findByStatusAndClass(String status, String classId) {
        return (List<Attendance>) hibernateTemplate.find("from Attendance where status=? and classId=?", status, classId);

    }

    @Override
    @Transactional
    public Attendance findById(int id) {
        return hibernateTemplate.get(Attendance.class, id);

    }

    @Override
    public List<Attendance> findBySemester(String semester) {
        return jdbcTemplate.queryForList("select * from attendance where studentid like '%" + semester + "%'", Attendance.class);

    }

    @Override
    public List<Attendance> findByYear(int year) {
        return jdbcTemplate.queryForList("select * from attendance where studentid like '%" + year + "'", Attendance.class);

    }

    @Override
    public List<Attendance> findBySemesterAndYear(String semester, String year) {
        return jdbcTemplate.queryForList("select * from attendance where studentid like '%" + semester + "%' and year like '%" + year + "'", Attendance.class);

    }

    @Override
    @Transactional
    public List<Attendance> findByStudentId(String studentId) {
        return (List<Attendance>) hibernateTemplate.find("from Attendance where studentId=?", studentId);
    }

    @Override
    public <T> List<T> get(String sql, Class<T> type) {
        return jdbcTemplate.queryForList(sql, type);
    }
    
    @Override
    public <T> List<T> getCustom(String sql, Class<T> type){
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(type));
    }
}
