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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public int save(UnitTest ut) {
        return (int) hibernateTemplate.save(ut);
    }

    @Override
    @Transactional
    public boolean update(UnitTest ut) {
        hibernateTemplate.update(ut);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(UnitTest ut) {
        hibernateTemplate.delete(ut);
        return true;
    }

    @Override
    @Transactional
    public List<UnitTest> findAll() {
        return hibernateTemplate.loadAll(UnitTest.class);
    }

    @Override
    @Transactional
    public List<UnitTest> findByUnitTest(String unitTest) {
        return (List<UnitTest>) hibernateTemplate.find("from UnitTest where unitTest = ? ", unitTest);
    }

    @Override
    @Transactional
    public List<UnitTest> findBySemesterAndAcademicyearAndYear(String semester, String academicyear, int year) {
        return (List<UnitTest>) hibernateTemplate.find("from UnitTest where semester = ? and acadamicYear = ? and year = ?", semester,academicyear,year);
    }

    @Override
    public <T> List<T> get(String query, Class<T> type) {
        return jdbcTemplate.queryForList(query, type);
    }

    @Override
    @Transactional
    public boolean saveAll(List<UnitTest> list) {
        for(UnitTest test : list) {
            hibernateTemplate.save(test);
        }
        return true;
    }

}
