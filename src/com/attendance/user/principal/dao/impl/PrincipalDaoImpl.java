/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.user.principal.dao.impl;

import com.attendance.faculty.model.Faculty;
import com.attendance.user.principal.dao.PrincipalDao;
import com.attendance.user.principal.model.Principal;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class PrincipalDaoImpl implements PrincipalDao {

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
    public String savePrincipal(Principal s) {
        return (String) hibernateTemplate.save(s);
    }

    @Override
    @Transactional
    public boolean updatePrincipal(Principal s) {
        try {
            hibernateTemplate.update(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePrincipal(Principal s) {
        try {
            hibernateTemplate.delete(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Principal findById(String id) {
        return hibernateTemplate.get(Principal.class, id);
    }

    @Override
    public List<Principal> findByGender(String gender) {
        return jdbcTemplate.queryForList("select * from principal where gender='" + gender + "'", Principal.class);
    }

    @Override
    @Transactional
    public List<Principal> findAll() {
        return hibernateTemplate.loadAll(Principal.class);
    }

    @Override
    @Transactional
    public List<Principal> findByName(String name) {
        return (List<Principal>) hibernateTemplate.find("from Principal where name like ?", "%" + name + "%");
    }

    @Override
    public boolean updatePrincipalId(String newId, String oldId) {
        try {
            jdbcTemplate.execute("update principal set contact='" + newId + "' where contact='" + oldId + "'");
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public <T> List<T> get(String query, Class<T> t) {
        return jdbcTemplate.queryForList(query, t);
    }
}
