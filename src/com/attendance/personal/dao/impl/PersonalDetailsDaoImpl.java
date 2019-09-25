/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.personal.dao.impl;

import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class PersonalDetailsDaoImpl implements PersonalDetailsDao {

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
    public int save(PersonalDetails user) {
        return (int) hibernateTemplate.save(user);
    }

    @Override
    @Transactional
    public boolean update(PersonalDetails user) {
        hibernateTemplate.update(user);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(PersonalDetails user) {
        hibernateTemplate.delete(user);
        return true;
    }

    @Override
    @Transactional
    public PersonalDetails findById(int userId) {
        return hibernateTemplate.get(PersonalDetails.class, userId);
    }

    @Override
    @Transactional
    public List<PersonalDetails> findAll() {
        return hibernateTemplate.loadAll(PersonalDetails.class);
    }

    @Override
    public List<PersonalDetails> findByName(String name) {
        return jdbcTemplate.query("select * from personaldetails where name like '%" + name + "%'", new BeanPropertyRowMapper<PersonalDetails>(PersonalDetails.class));
    }

    @Override
    public List<PersonalDetails> findByGender(String gender) {
        return jdbcTemplate.query("select * from personaldetails where gender='" + gender + "'", new BeanPropertyRowMapper<PersonalDetails>(PersonalDetails.class));
    }

}
