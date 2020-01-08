/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.dao.impl;

import com.attendance.login.user.model.User;
import com.attendance.login.dao.Login;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class UserLogin implements Login {
    
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
    public int save(User user) {
        Integer id = (Integer) hibernateTemplate.save(user);
        return id;
    }
    
    @Override
    @Transactional
    public boolean update(User user) {
        hibernateTemplate.update(user);
        return true;
    }
    
    @Override
    @Transactional
    public boolean delete(User user) {
        hibernateTemplate.delete(user);
        return true;
    }
    
    @Override
    @Transactional
    public User findById(int userId) {
        return hibernateTemplate.get(User.class, userId);
    }
    
    @Override
    @Transactional
    public User findByUsername(String username) {
        return ((List<User>) hibernateTemplate.findByNamedParam("from User where username=:username", "username", username)).get(0);
    }
    
    @Override
    @Transactional
    public List<User> findAll() {
        return (List<User>) hibernateTemplate.loadAll(User.class);
    }
    
    @Override
    @Transactional
    public List<User> findByType(String type) {
        return (List<User>) hibernateTemplate.find("from User where type=?", type);
        
    }
    
    @Override
    public int isUsernameExists(String username) {
        return (Integer) jdbcTemplate.queryForObject("select count(*) from loginuser where username=?", new Object[]{username}, Integer.class);
    }
    
    @Override
    @Transactional
    public List<User> findByDepartment(String department) {
        return (List<User>) hibernateTemplate.find("from User where department=?", department);
    }
    
    @Override
    @Transactional
    public User findByUsernameDepartmentType(String username, String department, String type) {
        return ((List<User>) hibernateTemplate.find("from User where username=? and department=? and type=?", username, department, type)).get(0);
        
    }
    
    @Override
    public int count(String sql) {
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    @Override
    @Transactional
    public List<User> findByStatus(String status) {
        return (List<User>) hibernateTemplate.find("From User where status = ?", status);
    }
    
    @Override
    @Transactional
    public List<User> findByStatusAndDepartment(String status, String department) {
        return (List<User>) hibernateTemplate.find("From User where status = ? and department = ?", status, department);
    }
    
}
