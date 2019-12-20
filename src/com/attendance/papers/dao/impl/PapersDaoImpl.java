/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.papers.dao.impl;

import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class PapersDaoImpl implements PapersDao {

    private HibernateTemplate hibernateTemplate;
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    @Transactional
    public long save(Paper p) {
        return (long) hibernateTemplate.save(p);
    }

    @Override
    @Transactional
    public boolean update(Paper p) {
        hibernateTemplate.update(p);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Paper p) {
        hibernateTemplate.delete(p);
        return true;
    }

    @Override
    @Transactional
    public Paper findByCode(String code) {
        return hibernateTemplate.get(Paper.class, code);
    }

    @Override
    @Transactional
    public List<Paper> findAll() {
        return hibernateTemplate.loadAll(Paper.class);
    }

    @Override
    @Transactional
    public List<Paper> findBySemester(String sem) {
        return (List<Paper>) hibernateTemplate.find("from Paper where semester=?", sem);
    }

    @Override
    public boolean updatePaperId(String newId, String oldId) {
        try {
            jdbcTemplate.execute("update papers set code='" + newId + "' where code='" + oldId + "'");
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public List<Paper> findByDepartment(String department) {
        return (List<Paper>) hibernateTemplate.find("from Paper where department=?", department);
    }

    @Override
    public List<Paper> findByCourseType(String courseType) {
        return (List<Paper>) hibernateTemplate.find("from Paper where coursetype=?", courseType);
    }

    @Override
    public List<Paper> findByDepartmentAndCourseType(String department, String courseType) {
        return (List<Paper>) hibernateTemplate.find("from Paper where department=? and courseType=?", department, courseType);
    }

    @Override
    @Transactional
    public boolean exists(String code) {
        return hibernateTemplate.find("from Paper where paperCode =?", code).size()>0;
    }

}
