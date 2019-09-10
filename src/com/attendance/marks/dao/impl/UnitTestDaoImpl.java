/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.dao.impl;

import com.attendance.marks.dao.UnitTestDao;
import com.attendance.marks.model.UnitTest;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 *
 * @author Programmer Hrishav
 */
public class UnitTestDaoImpl implements UnitTestDao {

    private JdbcTemplate jdbcTemplate;
    private HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(UnitTest ut) {
        return (int) hibernateTemplate.save(ut);
    }

    @Override
    public boolean update(UnitTest ut) {
        hibernateTemplate.update(ut);
        return true;
    }

    @Override
    public boolean delete(UnitTest ut) {
        hibernateTemplate.delete(ut);
        return true;
    }

    @Override
    public List<UnitTest> findAll() {
        return hibernateTemplate.loadAll(UnitTest.class);
    }

    @Override
    public List<UnitTest> findByUnitTest(String unitTest) {
        return (List<UnitTest>) hibernateTemplate.find("from UnitTest where ut = ? ", unitTest);
    }

    @Override
    public List<UnitTest> findBySemesterAndAcademicyearAndYear(String semester, String academicyear, int year) {
        return jdbcTemplate.query("select * from unittest where semester = '" + semester + "' and academicyear = '" + academicyear + "' and year = " + year, new BeanPropertyRowMapper<UnitTest>(UnitTest.class));
    }

    @Override
    public <T> List<T> get(String query, Class<T> type) {
        return jdbcTemplate.queryForList(query, type);
    }

}
