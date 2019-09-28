/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.activity.dao.impl;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class UserLoginActivity implements Activity {

    private JdbcTemplate jdbcTemplate;
    private HibernateTemplate hibernateTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    @Transactional
    public int save(LoginActivity login) {
        Integer result = (Integer) hibernateTemplate.save(login);
        return result;
    }

    @Override
    @Transactional
    public int update(LoginActivity login) {
        hibernateTemplate.update(login);
        return 0;
    }

    @Override
    public LoginActivity findByUsername(String username) {
        return jdbcTemplate.queryForObject("select * from loginactivity where username=?", new Object[]{username}, new BeanPropertyRowMapper<LoginActivity>(LoginActivity.class));
    }

    @Override
    public List<LoginActivity> findByDate(String date) {
        return jdbcTemplate.queryForList("select * from loginactivity where logindate='" + DateTime.parse(date).toString(DateTimeFormat.forPattern("yyyy-MM-dd")) + "'", LoginActivity.class);
    }

    @Override
    public List<LoginActivity> findByStatus(String status) {
        return jdbcTemplate.queryForList("select * from loginactivity where status='" + status + "'", LoginActivity.class);
    }

    @Override
    @Transactional
    public List<LoginActivity> findAll() {
        return (List<LoginActivity>) hibernateTemplate.loadAll(LoginActivity.class);
    }

    @Override
    @Transactional
    public List<LoginActivity> findByUserType(String type) {
        return (List<LoginActivity>) hibernateTemplate.find("from LoginActivity where usertype=?", type);
    }

    @Override
    public List<LoginActivity> get(String sql) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<LoginActivity>(LoginActivity.class));
    }

    @Override
    @Transactional
    public List<LoginActivity> findByDepartment(String department) {
        return (List<LoginActivity>) hibernateTemplate.find("from LoginActivity where department = ?", department);
    }

    @Override
    @Transactional
    public List<LoginActivity> findByName(String name) {
        return (List<LoginActivity>) hibernateTemplate.find("from LoginActivity where name=?", name);
    }

    
}
