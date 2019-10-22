/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.notes.dao.impl;

import com.attendance.notes.dao.NotesDao;
import com.attendance.notes.model.Notes;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 *
 * @author Programmer Hrishav
 */
public class NotesDaoImpl implements NotesDao {

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
    public Integer save(Notes notes) {
        return (Integer) hibernateTemplate.save(notes);
    }

    @Override
    @Transactional
    public boolean update(Notes notes) {
        hibernateTemplate.update(notes);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Notes notes) {
        hibernateTemplate.delete(notes);
        return true;
    }

    @Override
    @Transactional
    public List<Notes> findAll() {
        return hibernateTemplate.loadAll(Notes.class);
    }

    @Override
    public List<Notes> findByFaculty(String facultyName) {
        return (List<Notes>) hibernateTemplate.find("from Notes where facultyName like ?", facultyName + "%");
    }

    @Override
    public List<Notes> findByDate(String uploadDate) {
        return jdbcTemplate.query("select * from notes where uploaddate='" + uploadDate + "'", new BeanPropertyRowMapper<Notes>(Notes.class));
    }

    @Override
    public List<Notes> findByFileName(String fileName) {
        return jdbcTemplate.query("select * from notes where filename='" + fileName + "'", new BeanPropertyRowMapper<Notes>(Notes.class));
    }

    @Override
    public List<Notes> findByDateSorted(String uploadDate, String order) {
        return jdbcTemplate.query("select * from notes where uploaddate='" + uploadDate + "' order by str_to_date(uploaddate,'%d-%M-%y') " + order, new BeanPropertyRowMapper<Notes>(Notes.class));
    }

    @Override
    public List<Notes> sortBydate(String order) {
        return jdbcTemplate.query("select * from notes order by str_to_date(uploaddate,'%d-%m-%Y') " + order, new BeanPropertyRowMapper<Notes>(Notes.class));
    }

    @Override
    public List<Notes> sortByFileSize(String order) {
        return jdbcTemplate.query("select * from notes order by filesize " + order, new BeanPropertyRowMapper<Notes>(Notes.class));
    }

    @Override
    @Transactional
    public List<Notes> findByDepartment(String department) {
        return (List<Notes>) hibernateTemplate.find("from Notes where department =?", department);
    }

}
