/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.studentattendance.dao.impl;

import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import java.util.List;
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
    public ClassDetails findById(String classId) {
        return (ClassDetails) hibernateTemplate.get(ClassDetails.class, classId);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByFacultyName(String facultyName) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where facultyName=?", "%" + facultyName + "%");
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
    public List<ClassDetails> findBySemester(String semester) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where semester=?", semester);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByAcadamicYear(String acadamicYear) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where acadamicyear=?", acadamicYear);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByYear(int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where year=?", year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findBySemesterAndYear(String semester, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where semester=? and year=?", semester, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByAcadamicYearAndYear(String acadamicYear, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where acadamicyear=? and year=?", acadamicYear, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndSemesterAndPaperCodeAndYear(String department, String semester, String papercode, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and semester=? and paper=? and year=?", department, semester, papercode, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartment(String department) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=?", department);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndSemesterAndYear(String department, String semester, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and semester=? and year=?", department, semester, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndAcadamicYearAndYear(String department, String acadamicYear, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and acadamicyear=? and year=?", department, acadamicYear, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndCourseType(String department, String courseType) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and coursetype=?", department, courseType);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndCourseTypeAndYear(String department, String courseType, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and coursetype=? and year=?", department, courseType, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndCourseTypeAndAcadamicYearAndYear(String department, String courseType, String acadamicYear, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and coursetype=? and acadamicyear=? and year=?", department, courseType, acadamicYear, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByDepartmentAndCourseTypeAndSemesterAndYear(String department, String courseType, String semester, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and coursetype=? and semester=? and year=?", department, courseType, semester, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByFacultyAndDepartmentAndCourseTypeAndSemesterAndYear(String faculty, String department, String courseType, String semester, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where facultyName=? and department=? and coursetype=? and semester=? and year=?", "%" + faculty + "%", department, courseType, semester, year);
    }

    @Override
    @Transactional
    public List<ClassDetails> findByFacultyAndDepartmentAndCourseTypeAndAcadamicAndYear(String faculty, String department, String courseType, String acadamicYear, int year) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where facultyName=? and department=? and coursetype=? and acadamicyear=? and year=?", "%" + faculty + "%", department, courseType, acadamicYear, year);
    }

    @Override
    public List<ClassDetails> findAll(String department, String acadamicYear, String semester, int year, String papercode, String coursetype) {
        return (List<ClassDetails>) hibernateTemplate.find("from ClassDetails where department=? and acadamicyear=? and semester=? and year=? and paper=? and coursetype=?", department,acadamicYear,semester,year,papercode,coursetype);
    }

    @Override
    @Transactional
    public boolean updateClassId(String oldId, String newId) {
        jdbcTemplate.update("set foreign_key_checks=0");
        int p =jdbcTemplate.update("update classdetails set classId ='"+newId+"' where classId ='"+oldId+"'");
        int c =jdbcTemplate.update("update attendance set classId ='"+newId+"' where classId ='"+oldId+"'");
        jdbcTemplate.update("set foreign_key_checks=1");
        System.out.println("p="+p);
        System.out.println("c="+c);
        return (p==1) && (c>0);
    }
    
    
}
