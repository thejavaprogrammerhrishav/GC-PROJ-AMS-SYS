/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao.impl;

import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Programmer Hrishav
 */
public class ClassDetailsDaoImpl implements ClassDetailsDao {

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
    public String save(ClassDetails details) {
        return (String) hibernateTemplate.save(details);
    }

    @Override
    @Transactional
    public boolean update(ClassDetails details) {
        hibernateTemplate.update(details);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(ClassDetails details) {
        hibernateTemplate.delete(details);
        return true;
    }

    @Override
    @Transactional
    public List<ClassDetails> findAll() {
        return hibernateTemplate.loadAll(ClassDetails.class);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByFaculty(String facultyName) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where facultyName=?", facultyName);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDate(String date) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where date=?", date);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByTime(String time) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where time=?", time);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDateAndTime(String date, String time) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where date=? and time=?", date, time);
    }

    @Override
    public List<ClassDetails> findBySemester(String semester) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where semester=?", semester);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByYear(int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where year=?", year);
    }

    @Override
    @Transactional
    public ClassDetails findById(String classId) {
        return (ClassDetails) hibernateTemplate.get(ClassDetails.class, classId);
    }

    @Override
    @Transactional
    public List<ClassDetails> findBySubjectTaught(String subjectTaught) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where subjectTaught=?", subjectTaught);
    }

    @Override
    public int countFacultyClasses(String facultyName) {
        return jdbcTemplate.queryForObject("select * from classdetails where facultyName='" + facultyName + "'", Integer.class);
    }

    @Override
    public int countFacultyClassesDateRange(String facultyName, String dateFrom, String dateTo) {
        return jdbcTemplate.queryForObject("select * from classdetails where facultyName='" + facultyName + "' and date>='" + dateFrom + "' and date<=" + dateTo + "'", Integer.class);
    }

    @Override
    public int countfacultyClassesSemesterAndYear(String facultyName, String semester, String year) {
        return jdbcTemplate.queryForObject("select * from classdetails where facultyName='" + facultyName + "' and semester='" + semester + "' and year=" + year + "'", Integer.class);
    }

    @Override
    public int countFacutlyClassesSemester(String facultyName, String semseter) {
        return jdbcTemplate.queryForObject("select * from classdetails where facultyName='" + facultyName + "' and semester='" + semseter + "'", Integer.class);
    }

    @Override
    public int countClassesInEntireSemester(String semester, int year) {
        return jdbcTemplate.queryForObject("select * from classdetails where semester='" + semester + "' and year=" + year + "'", Integer.class);

    }

    @Override
    public <T> List<T> get(String sql, Class<T> type) {
        return jdbcTemplate.queryForList(sql, type);
    }

    @Override
    public int get(String sql) {
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    @Transactional
    public List<ClassDetails> findBySemesterAndYear(String semester, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where semester=? and year=?", semester, year);
    }

    @Override
    public boolean updateClassId(String newId, String oldId) {
        try {
            jdbcTemplate.execute("update classdetails set classId='" + newId + "' where classId='" + oldId + "'");
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartment(String department) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=?", department);
    }

    @Override
    public List<ClassDetails> findByCourseType(int coursetype) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where coursetype=?", coursetype);
    }

}
