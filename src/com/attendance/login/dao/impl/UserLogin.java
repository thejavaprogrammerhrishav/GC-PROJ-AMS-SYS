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
        Integer id=(Integer) hibernateTemplate.save(user);
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
    public List<User> findByName(String userName) {
        return jdbcTemplate.queryForList("select * from loginuser where name='" + userName + "'", User.class);
    }

    @Override
    public List<User> findByEmail(String userEmail) {
        return jdbcTemplate.queryForList("select * from loginuser where email='" + userEmail + "'", User.class);
    }

    @Override
    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject("select * from loginuser where username=?",new Object[] {username}, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    @Transactional
    public List<User> findByAll() {
        return (List<User>) hibernateTemplate.loadAll(User.class);
    }

    @Override
    public User findByUsernameAndEmail(String username, String userEmail) {
        return jdbcTemplate.queryForObject("select * from loginuser where username=? and email=?",new Object[] {username,userEmail}, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public List<User> findByType(String type) {
        return (List<User>) hibernateTemplate.find("from User where type=?", type);
        
    }

    @Override
    public User findByUsernameAndType(String username, String type) {
        return jdbcTemplate.queryForObject("select *from loginuser where username=? and type=?",new Object[] {username,type}, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public int isUsernameExists(String username) {
        return (Integer)jdbcTemplate.queryForObject("select count(*) from loginuser where username=?", new Object[]{username}, Integer.class);
    }   

    @Override
    public User findByUsernameTypeEmail(String username, String type, String email) {
        return jdbcTemplate.queryForObject("select *from loginuser where username=? and type=? and email=?",new Object[] {username,type,email}, new BeanPropertyRowMapper<User>(User.class));
    }

}
