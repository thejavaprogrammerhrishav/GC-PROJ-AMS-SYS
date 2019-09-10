/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.dao.impl;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultyDaoImpl implements FacultyDao {

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
    public String saveFaculty(Faculty s) {
        return (String) hibernateTemplate.save(s);
    }

    @Override
    @Transactional
    public boolean updateFaculty(Faculty s) {
        try {
            hibernateTemplate.update(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteFaculty(Faculty s) {
        try {
            hibernateTemplate.delete(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Faculty findById(String id) {
        return hibernateTemplate.get(Faculty.class, id);
    }

    @Override
    public List<Faculty> findByGender(String gender) {
        return jdbcTemplate.queryForList("select * from faculty where gender='" + gender + "'", Faculty.class);
    }

    @Override
    @Transactional
    public List<Faculty> findAll() {
        return hibernateTemplate.loadAll(Faculty.class);
    }

    @Override
    @Transactional
    public List<Faculty> findByFirstName(String firstname) {
        return (List<Faculty>) hibernateTemplate.find("from Faculty where firstName like ?", "%" + firstname + "%");
    }

    @Override
    @Transactional
    public List<Faculty> findByLastName(String lastname) {
        return (List<Faculty>) hibernateTemplate.find("from Faculty where lastName like ?", "%" + lastname + "%");
    }

    @Override
    public boolean updateFacultyId(String newId, String oldId) {
        try {
            jdbcTemplate.execute("update faculty set contact='" + newId + "' where contact='" + oldId + "'");
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public <T> List<T> get(String query, Class<T> t) {
        return jdbcTemplate.queryForList(query, t);
    }

    @Override
    @Transactional
    public List<Faculty> findByDepartment(String department) {
        return (List<Faculty>) hibernateTemplate.find("from Faculty where department like ?", department);
    }

}
