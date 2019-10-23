/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.dao.impl;

import com.attendance.notes.model.Notes;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pc
 */
public class RoutineDaoImpl implements RoutineDao {

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
    public Integer save(Routine r) {
        return (Integer) hibernateTemplate.save(r);
    }

    @Override
    @Transactional
    public boolean update(Routine r) {
        hibernateTemplate.update(r);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Routine r) {
        hibernateTemplate.delete(r);
        return true;
    }

    @Override
    @Transactional
    public List<Routine> findAll() {
        return hibernateTemplate.loadAll(Routine.class);
    }

    @Override
    @Transactional
    public Routine findById(Integer id) {
        return hibernateTemplate.get(Routine.class, id);
    }

    @Override
    @Transactional
    public List<Routine> findByDepartment(String department) {
        return (List<Routine>) hibernateTemplate.find("from Routine where department = ?", department);
    }

    @Override
    @Transactional
    public List<Routine> findByDate(String date) {
        return (List<Routine>) hibernateTemplate.find("from Routine where date = ?", date);
    }

    @Override
    @Transactional
    public List<Routine> findByDepartmentAndDate(String department, String date) {
        return (List<Routine>) hibernateTemplate.find("from Routine where department =? and date = ?", department, date);
    }

    @Override
    public <T> List<T> get(String sql, Class<T> type) {
        return jdbcTemplate.queryForList(sql, type);
    }

    @Override
    public List<Routine> sortByDepartment(String department, String order) {
        return jdbcTemplate.query("select * from routine where department = '" + department + "' order by str_to_date(date,'%d-%m-%Y') " + order, new BeanPropertyRowMapper<Routine>(Routine.class));
    }

    @Override
    @Transactional
    public List<Routine> findByDepartmentAndYear(String department, String year) {
        return (List<Routine>) hibernateTemplate.find("from Routine where department =? and date like ?", department, "%" + year);
    }

    @Override
    @Transactional
    public Routine findByDepartmentAndDateAndStatus(String department, String date, String status) {
        return ((List<Routine>) hibernateTemplate.find("from Routine where department =? and date like ? and status = ?", department, "%" + date, status)).get(0);
    }

    @Override
    @Transactional
    public List<Routine> findByDepartmentAndStatus(String department, String status) {
        return (List<Routine>) hibernateTemplate.find("from Routine where department =? and status = ?", department, status);
    }

    @Override
    public Integer hasActiveRoutine(String department, String year) {
        return jdbcTemplate.queryForObject("select count(*) from routine where department = '" + department + "' and date like '%" + year + "' and status = 'Active'", Integer.class);
    }

}
